package com.airwallex.data;

public enum InputItemType {

    NUMBER,
    OPERATOR,

    ;

    public static boolean isNumber(InputItemType inputItemType){
        return NUMBER == inputItemType;
    }

}
