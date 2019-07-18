package com.airwallex.display;

import com.airwallex.common.Constants;
import com.airwallex.data.InputNumber;
import com.airwallex.exception.OperationException;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * default display strategy
 */
public class DefaultDisplayStrategy implements DisplayStrategy {

    private static final String STACK_PREFIX = "stack: ";

    @Override
    public boolean match(String strategyName) {
        return Constants.DEFAULT_DISPLAY_STRATEGY_KEY.equalsIgnoreCase(strategyName);
    }

    @Override
    public void display(List<InputNumber> inputItemList, OperationException error) {
        String result = assembleDisplayContent(inputItemList, error);
        System.out.println(result);
    }

    @Override
    public String assembleDisplayContent(List<InputNumber> inputItemList, OperationException error) {
        StringBuilder result = new StringBuilder();
        if (error != null) {
            result.append(error.getMessage()+"\n");
        }
        result.append(STACK_PREFIX);
        if (CollectionUtils.isNotEmpty(inputItemList)) {
            inputItemList.forEach(
                    item -> result
                            .append(formatNumber(item.getValue()))
                            .append(" ")
            );
            result.delete(result.length() - 1, result.length()).toString();
        }
        return result.toString();
    }

    @Override
    public String formatNumber(BigDecimal number) {
        BigDecimal tmp = number.stripTrailingZeros();
        return tmp.scale() > 10 ?
                tmp.setScale(10, RoundingMode.DOWN).toString()
                : tmp.toPlainString();
    }

}
