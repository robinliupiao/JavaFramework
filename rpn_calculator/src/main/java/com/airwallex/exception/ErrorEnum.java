package com.airwallex.exception;


public enum ErrorEnum {

    INSUFFICIENT_PARAMETERS("1001", "operator {0} (position: {1}): insufficient parameters"),

    NOT_SUPPORTED_OPERATOR("1002", "operator {0} (position: {1}): not supported !"),

    NOT_SUPPORTED_MATH_OPERATOR("1003", "math operator {0} (position: {1}): not supported !"),
    ;


    private String code;

    private String message;


    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMessage(String... args) {
        String result = message;
        if (args == null || args.length == 0) {
            return result;
        }
        for (int i = 0; i < args.length; i++) {
            result = result.replaceAll("\\{" + String.valueOf(i) + "\\}", args[i]);
        }
        return result;
    }

}
