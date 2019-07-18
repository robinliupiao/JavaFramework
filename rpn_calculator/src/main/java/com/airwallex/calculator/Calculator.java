package com.airwallex.calculator;

import com.airwallex.data.InputItem;
import com.airwallex.data.InputItemType;
import com.airwallex.data.InputNumber;
import com.airwallex.data.InputOperator;
import com.airwallex.data.StackExpression;
import com.airwallex.exception.ErrorEnum;
import com.airwallex.exception.OperationException;
import com.airwallex.operation.Operation;
import com.airwallex.utils.ServiceUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * calculator class which contains logic to trigger calculate and maintain calculator context data
 */
public class Calculator {


    private static Map<String, Operation> operationMap;

    static {
        operationMap = ServiceUtils.getOperationMap();
    }

    private CalculatorContext context = new CalculatorContext();

    private InputItem currentStackItem;


    /**
     * calculation entry
     *
     * @param inputItemList
     * @return
     */
    public OperationException calculate(List<InputItem> inputItemList) {
        if (CollectionUtils.isEmpty(inputItemList)) {
            return null;
        }
        for (InputItem item : inputItemList) {
            OperationException error = null;
            // update current item of working stack
            currentStackItem = item;
            if (InputItemType.isNumber(item.getType())) {
                // if number, push to stack
                pushStack((InputNumber) item);
            } else {
                // if operator, use operator strategy to do operate
                InputOperator operator = (InputOperator) item;
                error = operate(operator);
            }
            if (error != null) {
                return error;
            }
        }
        // reset the working stack item position
        resetStackPosition();
        return null;
    }

    public int getStackSize() {
        return context.getWorkingStack().size();
    }

    public void clearStack() {
        context.getWorkingStack().clear();
    }

    public void pushStack(InputNumber number) {
        number.setValue(formatNumber(number.getValue()));
        context.getWorkingStack().push(number);
    }

    public void pushStack(List<InputNumber> numbers) {
        numbers.forEach(this::pushStack);
    }

    public InputNumber popStack() {
        return CollectionUtils.isEmpty(context.getWorkingStack()) ? null : context.getWorkingStack().pop();
    }

    public void replaceStack(List<InputNumber> inputNumbers) {
        clearStack();
        if (CollectionUtils.isEmpty(inputNumbers)) {
            return;
        }
        pushStack(inputNumbers);
    }

    /**
     * get working stack from old to new
     *
     * @return
     */
    public List<InputNumber> getStack() {
        Deque<InputNumber> list = context.getWorkingStack();
        Iterator<InputNumber> iterator = list.descendingIterator();
        List<InputNumber> result = new ArrayList<>(list.size());
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    public void pushHistoryExpression(List<InputNumber> inputNumbers, InputOperator operator) {
        context.getHistoryCalculation().push(new StackExpression(inputNumbers, operator));
    }

    public List<InputNumber> popHistoryOperands() {
        return context.getLatestHistoryOperands();
    }


    public InputItem getCurrentStackItem() {
        return currentStackItem;
    }


    /**
     * clear all calculator context
     */
    public void clearAll() {
        context.clear();
        this.currentStackItem = null;
    }

    /**
     * operate on the stack
     *
     * @param operator
     * @return
     */
    private OperationException operate(InputOperator operator) {
        // use operator strategy to do operate
        Operation operationStrategy = getOperationStrategy(operator.getValue());
        if (operationStrategy == null) {
            // if no operator strategy,return no supported operator error
            return OperationException.newInstance(
                    ErrorEnum.NOT_SUPPORTED_OPERATOR,
                    operator.getValue(),
                    String.valueOf(operator.getPosition()));
        }
        try {
            //do operation by different operator
            operationStrategy.operate(this);
        } catch (OperationException oe) {
            return oe;
        }
        return null;
    }

    /**
     * get concrete operation by operator
     *
     * @param operatorName
     * @return
     */
    private Operation getOperationStrategy(String operatorName) {
        if (StringUtils.isEmpty(operatorName)) {
            return null;
        }
        return operationMap.get(operatorName.toLowerCase());
    }

    /**
     * format number when store into stack
     *
     * @param number
     * @return
     */
    private BigDecimal formatNumber(BigDecimal number) {
        return number.scale() < 15 ?
                number.setScale(15, RoundingMode.DOWN)
                : number;
    }

    /**
     * reset stack position from top to end
     */
    private void resetStackPosition() {
        List<InputNumber> stack = getStack();
        for (int i = 0; i < stack.size(); i++) {
            stack.get(i).setPosition(i + 1);
        }

    }


}
