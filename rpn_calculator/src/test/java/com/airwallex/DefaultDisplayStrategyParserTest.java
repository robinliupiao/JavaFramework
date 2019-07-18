package com.airwallex;

import com.airwallex.common.Constants;
import com.airwallex.data.InputNumber;
import com.airwallex.display.DefaultDisplayStrategy;
import com.airwallex.exception.ErrorEnum;
import com.airwallex.exception.OperationException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultDisplayStrategyParserTest {

    private DefaultDisplayStrategy strategy;

    {
        strategy = new DefaultDisplayStrategy();
    }

    private List<InputNumber> buildInputNumberList(BigDecimal... data) {
        return Arrays.stream(data).map(InputNumber::new).collect(Collectors.toList());
    }

    private String buildErrorMessage(ErrorEnum error,String...args){
        return error.getMessage(args);
    }

    @Test
    public void testMatch() {
        assertTrue(strategy.match(Constants.DEFAULT_DISPLAY_STRATEGY_KEY));
    }

    @Test
    public void testFormatNumber() {

        // less than 10 decimal places
        String result = strategy.formatNumber(new BigDecimal("1.23"));
        assertEquals("1.23", result);

        // more than 10 decimal places
        result = strategy.formatNumber(new BigDecimal("1.222222222222222"));
        assertEquals("1.2222222222", result);

    }

    @Test
    public void testAssembleDisplayContent() {
        List<InputNumber> inputItemList = buildInputNumberList(BigDecimal.ONE, BigDecimal.TEN);
        OperationException error = OperationException.newInstance(ErrorEnum.INSUFFICIENT_PARAMETERS,
                "+", "2");
        String result = strategy.assembleDisplayContent(inputItemList, error);

        String expected = buildErrorMessage(ErrorEnum.INSUFFICIENT_PARAMETERS,"+","2")
                +"\n"
                + "stack: 1 10";

        assertEquals(expected, result);

    }


}
