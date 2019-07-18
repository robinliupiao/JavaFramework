package com.airwallex.parser;

import com.airwallex.data.InputItem;
import com.airwallex.data.InputNumber;
import com.airwallex.data.InputOperator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * parse command line to input
 */
public class CommandLineParser {

    public List<InputItem> parseLine(String line) {
        if (StringUtils.isBlank(line)) {
            return null;
        }
        String[] inputArray = line.trim().split("\\s+");
        List<InputItem> result = new ArrayList<>(inputArray.length);

        for (int i = 0; i < inputArray.length; i++) {
            String input = inputArray[i];
            int position = 2 * i + 1;
            if (NumberUtils.isNumber(input)) {
                result.add(new InputNumber(position, new BigDecimal(input)));
            } else {
                result.add(new InputOperator(position, input));
            }
        }
        return result;
    }


}
