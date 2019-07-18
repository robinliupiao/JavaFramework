package com.airwallex.operation;

import com.airwallex.data.InputNumber;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class SqrtOperationTest {

    private SqrtOperation operation;


    {
        operation = new SqrtOperation();
    }


    private List<InputNumber> buildInputNumberList(BigDecimal... data) {
        return Arrays.stream(data).map(InputNumber::new).collect(Collectors.toList());
    }

    @Test
    public void testDoOperate() {
        List<InputNumber> operands = buildInputNumberList(new BigDecimal("4"));

        BigDecimal result = operation.doOperate(operands);
        assertEquals(new BigDecimal("2"), result);
    }

}
