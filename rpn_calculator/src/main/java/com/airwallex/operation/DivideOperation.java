package com.airwallex.operation;

import com.airwallex.calculator.Calculator;
import com.airwallex.data.InputNumber;
import com.airwallex.exception.ErrorEnum;
import com.airwallex.exception.OperationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DivideOperation implements MathsOperation {


    @Override
    public List<InputNumber> getOperandList(Calculator calculator) throws OperationException {
        return getTwoOperandList(calculator);
    }

    @Override
    public BigDecimal doOperate(List<InputNumber> operands) throws OperationException {
        if (BigDecimal.ZERO.equals(operands.get(1).getValue())) {
            throw OperationException.newInstance(ErrorEnum.NOT_SUPPORTED_MATH_OPERATOR,
                    getRepresentation(),
                    String.valueOf(operands.get(1).getPosition()));
        }
        return operands.get(0).getValue().divide(operands.get(1).getValue(),RoundingMode.HALF_UP);
    }

    @Override
    public String getRepresentation() {
        return "/";
    }

}
