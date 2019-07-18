package com.airwallex.data;

import lombok.Data;

import java.math.BigDecimal;

/**
 * the input number item which was separated by space
 */
@Data
public class InputNumber extends InputItem<BigDecimal> {

    public InputNumber(BigDecimal value) {
        super();
        setValue(value);
        setType(InputItemType.NUMBER);
    }

    public InputNumber(int position, BigDecimal value) {
        super(InputItemType.NUMBER, position, value);
    }

}
