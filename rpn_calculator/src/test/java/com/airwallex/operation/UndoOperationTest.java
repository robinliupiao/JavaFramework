package com.airwallex.operation;

import com.airwallex.calculator.Calculator;
import com.airwallex.data.InputItem;
import com.airwallex.data.InputNumber;
import com.airwallex.parser.CommandLineParser;
import org.junit.After;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class UndoOperationTest {

    private UndoOperation operation;

    private Calculator calculator;

    private CommandLineParser parser;

    {
        operation = new UndoOperation();
        calculator = new Calculator();
        parser = new CommandLineParser();
    }

    @After
    public void afterTestCase() {
        calculator.clearAll();
    }


    private List<InputNumber> buildInputNumberList(BigDecimal... data) {
        return Arrays.stream(data).map(InputNumber::new).collect(Collectors.toList());
    }

    private List<InputItem> buildInputList(String line) {
        List<InputItem> inputItemList = parser.parseLine(line);
        return inputItemList;
    }

    @Test
    public void testOperate() {
        List<InputNumber> operands = buildInputNumberList(BigDecimal.ONE, BigDecimal.TEN);

        // empty history case
        calculator.pushStack(operands);
        operation.operate(calculator);
        assertTrue(calculator.getStackSize() == 1);
        operation.operate(calculator);
        assertTrue(calculator.getStackSize() == 0);

        // not empty history case
        List<InputItem> firstInput = buildInputList("1 2 +");
        calculator.calculate(firstInput);
        assertTrue(calculator.getStackSize() == 1);
        assertTrue(calculator.getStack().get(0).getValue().setScale(0,RoundingMode.DOWN).equals(new BigDecimal("3")));
        operation.operate(calculator);
        assertTrue(calculator.getStackSize() == 2);
        assertTrue(calculator.getStack().get(0).getValue().setScale(0,RoundingMode.DOWN).equals(new BigDecimal("1")));
        assertTrue(calculator.getStack().get(1).getValue().setScale(0,RoundingMode.DOWN).equals(new BigDecimal("2")));

    }

}
