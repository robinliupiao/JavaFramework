package com.airwallex.operation;

import com.airwallex.data.InputNumber;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class AddOperationTest {

    private AddOperation operation;


    {
        operation = new AddOperation();
    }


    private List<InputNumber> buildInputNumberList(BigDecimal... data) {
        return Arrays.stream(data).map(InputNumber::new).collect(Collectors.toList());
    }

    @Test
    public void testDoOperate() {
        List<InputNumber> operands = buildInputNumberList(BigDecimal.TEN, BigDecimal.TEN);

        BigDecimal result = operation.doOperate(operands);
        assertEquals(new BigDecimal("20"), result);
    }

}
