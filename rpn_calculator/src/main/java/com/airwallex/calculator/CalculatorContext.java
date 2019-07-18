package com.airwallex.calculator;

import com.airwallex.data.InputNumber;
import com.airwallex.data.StackExpression;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Data
public class CalculatorContext {


    private static ThreadLocal<Calculator> calculatorThreadLocal = ThreadLocal.withInitial(() -> new Calculator());
    private Deque<InputNumber> workingStack = new LinkedList<>();
    private Deque<StackExpression> historyCalculation = new LinkedList<>();

    public static Calculator getCalculator() {
        return calculatorThreadLocal.get();
    }

    public static void destroyCalculator() {
        calculatorThreadLocal.remove();
    }

    public void clear() {
        this.workingStack.clear();
        this.historyCalculation.clear();
    }

    public List<InputNumber> getLatestHistoryOperands() {
        StackExpression expression = getLatestHistoryExpression();
        return expression == null ? null : expression.getNumberStack();
    }

    public StackExpression getLatestHistoryExpression() {
        return CollectionUtils.isEmpty(historyCalculation) ? null : historyCalculation.pop();
    }


}
