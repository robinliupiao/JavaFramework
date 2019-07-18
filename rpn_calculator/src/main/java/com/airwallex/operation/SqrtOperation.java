package com.airwallex.operation;

import com.airwallex.calculator.Calculator;
import com.airwallex.data.InputNumber;
import com.airwallex.exception.OperationException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class SqrtOperation implements MathsOperation {


    @Override
    public List<InputNumber> getOperandList(Calculator calculator) throws OperationException {
        return getOneOperandList(calculator);
    }

    @Override
    public BigDecimal doOperate(List<InputNumber> operands) throws OperationException {
        return sqrt(operands.get(0).getValue());
    }

    @Override
    public String getRepresentation() {
        return "sqrt";
    }


    private BigDecimal sqrt(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal x = num.divide(new BigDecimal("2"), MathContext.DECIMAL128);
        while (x.subtract(x = sqrtIteration(x, num)).abs().compareTo(new BigDecimal("0.00000000000000000001")) > 0) ;
        return x;
    }

    private BigDecimal sqrtIteration(BigDecimal x, BigDecimal n) {
        return x.add(n.divide(x, MathContext.DECIMAL128)).divide(new BigDecimal("2"), MathContext.DECIMAL128);
    }

}
