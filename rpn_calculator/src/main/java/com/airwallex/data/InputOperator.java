package com.airwallex.data;

import lombok.Data;

/**
 * the input operator item which was separated by space
 */
@Data
public class InputOperator extends InputItem<String> {

    public InputOperator(String value) {
        super();
        setValue(value);
        setType(InputItemType.OPERATOR);
    }

    public InputOperator(int position, String operatorRepresentation) {
        super(InputItemType.OPERATOR, position, operatorRepresentation);
    }

}
