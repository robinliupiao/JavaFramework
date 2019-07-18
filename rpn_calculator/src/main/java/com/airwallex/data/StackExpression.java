package com.airwallex.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * the current stack state represented by expression when doing operation
 */
@Data
@AllArgsConstructor
public class StackExpression {

    private List<InputNumber> numberStack;

    private InputOperator operator;

    public StackExpression(InputOperator operator, InputNumber... inputNumbers) {
        this.operator = operator;
        pushNumber(inputNumbers);
    }


    public StackExpression pushNumber(InputNumber... inputNumbers) {
        if (inputNumbers == null || inputNumbers.length == 0) {
            return this;
        }
        if (this.numberStack == null) {
            this.numberStack = new LinkedList<>();
        }
        Arrays.stream(inputNumbers)
                .forEach(numberStack::add);
        return this;
    }

}
