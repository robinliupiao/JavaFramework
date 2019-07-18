package com.airwallex.exception;

import lombok.Data;

@Data
public class OperationException extends RuntimeException {

    private String code;

    private OperationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public static OperationException newInstance(String code, String message) {
        return new OperationException(code, message);
    }


    public static OperationException newInstance(ErrorEnum error, String... args) {

        return newInstance(error.getCode(), error.getMessage(args));
    }


}
