package com.airwallex;

import com.airwallex.calculator.Calculator;
import com.airwallex.data.InputItem;
import com.airwallex.data.InputNumber;
import com.airwallex.data.InputOperator;
import com.airwallex.exception.OperationException;
import com.airwallex.parser.CommandLineParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CalculatorTest {

    private Calculator calculator;

    private CommandLineParser parser;

    {
        parser = new CommandLineParser();
        calculator = new Calculator();
    }


    private List<InputItem> buildCorrectInputList() {
        return buildInputList("3 4 5 1 +");
    }

    private List<InputItem> buildErrorInputList() {
        return buildInputList("1 -");
    }

    private List<InputItem> buildInputList(String line) {
        List<InputItem> inputItemList = parser.parseLine(line);
        return inputItemList;
    }

    private InputNumber buildOneInputNumber(BigDecimal data) {
        return new InputNumber(data);
    }

    private List<InputNumber> buildInputNumberList(BigDecimal... data) {
        return Arrays.stream(data).map(this::buildOneInputNumber).collect(Collectors.toList());
    }

    private InputOperator buildOperator(String operator) {
        return new InputOperator(operator);
    }


    @After
    public void afterTestCase() {
        calculator.clearAll();
    }

    @Test
    public void testClearStack() {
        assertTrue(calculator.getStack().size() == 0);

        List<InputItem> inputItemList = buildCorrectInputList();
        calculator.calculate(inputItemList);
        assertTrue(calculator.getStack().size() > 0);

        calculator.clearStack();

        assertTrue(calculator.getStack().size() == 0);
    }

    @Test
    public void testPushStack() {
        assertTrue(calculator.getStack().size() == 0);

        InputNumber inputNumber = buildOneInputNumber(BigDecimal.ZERO);
        calculator.pushStack(inputNumber);

        assertEquals(1, calculator.getStackSize());
    }

    @Test
    public void testBatchPushStack() {
        assertTrue(calculator.getStack().size() == 0);

        List<InputNumber> inputNumbers = buildInputNumberList(BigDecimal.ONE, BigDecimal.TEN);
        calculator.pushStack(inputNumbers);

        assertEquals(2, calculator.getStackSize());
    }

    @Test
    public void testPopStack() {
        assertTrue(calculator.getStack().size() == 0);

        InputNumber inputNumber = buildOneInputNumber(BigDecimal.ZERO);
        calculator.pushStack(inputNumber);

        InputNumber poped = calculator.popStack();

        assertEquals(inputNumber, poped);
    }

    @Test
    public void testReplaceStack() {

        List<InputNumber> inputNumbers = buildInputNumberList(BigDecimal.ONE);
        calculator.pushStack(inputNumbers);
        assertEquals(1, calculator.getStackSize());

        inputNumbers = buildInputNumberList(BigDecimal.ONE, BigDecimal.TEN);
        calculator.replaceStack(inputNumbers);

        assertEquals(2, calculator.getStackSize());
    }

    @Test
    public void testPushHistoryExpression() {

        List<InputNumber> historyNumbers = calculator.popHistoryOperands();
        assertNull(historyNumbers);

        InputOperator operator = buildOperator("+");
        historyNumbers = buildInputNumberList(BigDecimal.ZERO, BigDecimal.ONE);
        calculator.pushHistoryExpression(historyNumbers, operator);

        List<InputNumber> popedHistoryNumbers = calculator.popHistoryOperands();
        assertEquals(historyNumbers.size(), popedHistoryNumbers.size());
        assertEquals(BigDecimal.ZERO, popedHistoryNumbers.get(0).getValue());
        assertEquals(BigDecimal.ONE, popedHistoryNumbers.get(1).getValue());
    }

    @Test
    public void testPopHistoryOperands() {

        InputOperator operator = buildOperator("+");
        List<InputNumber> historyNumbers = buildInputNumberList(BigDecimal.ZERO, BigDecimal.ONE);
        calculator.pushHistoryExpression(historyNumbers, operator);

        List<InputNumber> popedHistoryNumbers = calculator.popHistoryOperands();
        assertEquals(historyNumbers.size(), popedHistoryNumbers.size());
        assertEquals(BigDecimal.ZERO, popedHistoryNumbers.get(0).getValue());
        assertEquals(BigDecimal.ONE, popedHistoryNumbers.get(1).getValue());
    }


    @Test
    public void testClearAll() {
        assertTrue(calculator.getStack().size() == 0);

        List<InputItem> inputItemList = buildCorrectInputList();
        calculator.calculate(inputItemList);
        assertTrue(calculator.getStack().size() > 0);

        calculator.clearAll();

        assertTrue(calculator.getStack().size() == 0);
    }


    @Test
    public void testCalculate() {
        List<InputItem> inputItemList = buildCorrectInputList();
        OperationException exception = calculator.calculate(inputItemList);
        assertNull(exception);

        calculator.clearAll();

        List<InputItem> errorInputList = buildErrorInputList();
        OperationException error = calculator.calculate(errorInputList);
        Assert.assertNotNull(error);
    }

}
