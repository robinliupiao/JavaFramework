package com.airwallex.operation;

import com.airwallex.calculator.Calculator;
import com.airwallex.data.InputNumber;
import com.airwallex.exception.OperationException;

import java.math.BigDecimal;
import java.util.List;

public class AddOperation implements MathsOperation {


    @Override
    public List<InputNumber> getOperandList(Calculator calculator) throws OperationException {
        return getTwoOperandList(calculator);
    }

    @Override
    public BigDecimal doOperate(List<InputNumber> operands) throws OperationException {
        return operands.get(0).getValue().add(operands.get(1).getValue());
    }

    @Override
    public String getRepresentation() {
        return "+";
    }

}
