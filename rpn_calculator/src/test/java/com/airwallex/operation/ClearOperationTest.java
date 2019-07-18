package com.airwallex.operation;

import com.airwallex.calculator.Calculator;
import com.airwallex.data.InputNumber;
import org.junit.After;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class ClearOperationTest {

    private ClearOperation operation;

    private Calculator calculator;


    {
        operation = new ClearOperation();
        calculator = new Calculator();
    }

    @After
    public void afterTestCase() {
        calculator.clearAll();
    }


    private List<InputNumber> buildInputNumberList(BigDecimal... data) {
        return Arrays.stream(data).map(InputNumber::new).collect(Collectors.toList());
    }

    @Test
    public void testOperate() {
        List<InputNumber> operands = buildInputNumberList(BigDecimal.TEN, BigDecimal.TEN);

        calculator.pushStack(operands);
        assertTrue(calculator.getStackSize() == 2);

        operation.operate(calculator);
        assertTrue(calculator.getStackSize() == 0);
    }

}
