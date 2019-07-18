package com.airwallex;

import com.airwallex.data.InputItem;
import com.airwallex.parser.CommandLineParser;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CommandLineParserTest {

    private CommandLineParser parser;

    {
        parser = new CommandLineParser();
    }

    @Test
    public void testParseLine() {
        String line = "";
        // empty case
        List<InputItem> inputItemList = parser.parseLine(line);
        assertNull(inputItemList);

        // not empty case
        line = "1 2 +";
        inputItemList = parser.parseLine(line);
        assertEquals(3, inputItemList.size());
        assertEquals(new BigDecimal(1), inputItemList.get(0).getValue());
        assertEquals(new BigDecimal(2), inputItemList.get(1).getValue());
        assertEquals("+", inputItemList.get(2).getValue());
    }


}
