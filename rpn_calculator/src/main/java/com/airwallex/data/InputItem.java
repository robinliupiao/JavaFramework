package com.airwallex.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * the input data item which was separated by space
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputItem<T> {

    InputItemType type;
    private int position = -1;
    private T value;

}
