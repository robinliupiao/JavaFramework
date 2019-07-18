package com.airwallex.display;

import com.airwallex.common.ServiceMatcher;
import com.airwallex.data.InputNumber;
import com.airwallex.exception.OperationException;

import java.math.BigDecimal;
import java.util.List;

/**
 * the display strategy interface which can process the InputNumber list and display them
 */
public interface DisplayStrategy extends ServiceMatcher {

    /**
     * display input number
     *
     * @param inputItemList
     * @param error
     */
    void display(List<InputNumber> inputItemList, OperationException error);

    /**
     * assemble display content
     * @param inputItemList
     * @param error
     * @return
     */
    String assembleDisplayContent(List<InputNumber> inputItemList, OperationException error);

    /**
     * format the displayed number
     *
     * @param number
     * @return
     */
    String formatNumber(BigDecimal number);

}
