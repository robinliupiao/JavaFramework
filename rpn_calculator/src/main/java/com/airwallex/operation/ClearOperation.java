package com.airwallex.operation;


import com.airwallex.calculator.Calculator;
import com.airwallex.exception.OperationException;

public class ClearOperation implements Operation {

    @Override
    public String getRepresentation() {
        return "clear";
    }

    @Override
    public void operate(Calculator calculator) throws OperationException {
        calculator.clearAll();
    }

}
