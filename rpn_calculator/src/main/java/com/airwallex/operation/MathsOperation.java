package com.airwallex.operation;

import com.airwallex.calculator.Calculator;
import com.airwallex.data.InputNumber;
import com.airwallex.data.InputOperator;
import com.airwallex.exception.ErrorEnum;
import com.airwallex.exception.OperationException;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * math operation base interface
 */
public interface MathsOperation extends Operation {


    /**
     * base operate logic for math operation
     * @param calculator
     * @throws OperationException
     */
    @Override
    default void operate(Calculator calculator) throws OperationException {
        // extract the operands list
        List<InputNumber> operandsList = getOperandList(calculator);
        if (CollectionUtils.isEmpty(operandsList)) {
            // when not sufficient parameter
            throw OperationException.newInstance(ErrorEnum.INSUFFICIENT_PARAMETERS,
                    this.getRepresentation(),
                    String.valueOf(calculator.getCurrentStackItem().getPosition()));
        }

        // do customized operate
        BigDecimal decimalResult = this.doOperate(operandsList);
        InputNumber result = new InputNumber(decimalResult);

        //  push old stack to history stack expression before calculation
        List<InputNumber> oldStack = calculator.getStack();
        oldStack.addAll(operandsList);
        calculator.pushHistoryExpression(oldStack, (InputOperator) calculator.getCurrentStackItem());

        // push result to working stack
        calculator.pushStack(result);
    }

    /**
     * get two operand from stack
     * @param calculator
     * @return
     * @throws OperationException
     */
    default List<InputNumber> getTwoOperandList(Calculator calculator) throws OperationException {
        if (hasTwoOperands(calculator)) {
            InputNumber latestStackNumber = calculator.popStack();
            InputNumber beforeLatestStackNumber = calculator.popStack();
            return Arrays.asList(beforeLatestStackNumber, latestStackNumber);
        }
        return null;
    }

    /**
     * get one operand from stack
     * @param calculator
     * @return
     * @throws OperationException
     */
    default List<InputNumber> getOneOperandList(Calculator calculator) throws OperationException {
        if (hasOperand(calculator)) {
            return Arrays.asList(calculator.popStack());
        }
        return null;
    }

    default boolean hasTwoOperands(Calculator calculator) {
        return calculator.getStackSize() > 1;
    }

    default boolean hasOperand(Calculator calculator) {
        return calculator.getStackSize() > 0;
    }

    /**
     * get the operands list to use operator to process
     *
     * @param calculator
     * @return
     * @throws com.airwallex.exception.OperationException
     */
    List<InputNumber> getOperandList(Calculator calculator) throws OperationException;

    /**
     * customized operation logic
     *
     * @param operands
     * @return
     * @throws OperationException
     */
    BigDecimal doOperate(List<InputNumber> operands) throws OperationException;

}
