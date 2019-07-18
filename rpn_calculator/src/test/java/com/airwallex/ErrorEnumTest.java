package com.airwallex;

import com.airwallex.exception.ErrorEnum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ErrorEnumTest {

    @Test
    public void testGetMessage() {
        String expected = "operator + (position: 2): insufficient parameters";

        assertEquals(expected, ErrorEnum.INSUFFICIENT_PARAMETERS.getMessage("+", "2"));

    }


}
