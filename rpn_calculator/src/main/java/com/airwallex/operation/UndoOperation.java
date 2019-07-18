package com.airwallex.operation;


import com.airwallex.calculator.Calculator;
import com.airwallex.data.InputNumber;
import com.airwallex.exception.OperationException;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class UndoOperation implements Operation {

    @Override
    public String getRepresentation() {
        return "undo";
    }

    @Override
    public void operate(Calculator calculator) throws OperationException {
        // pop from latest history
        List<InputNumber> hisNumbers = calculator.popHistoryOperands();
        if (CollectionUtils.isEmpty(hisNumbers)) {
            // pop from stack
            calculator.popStack();
        } else {
            // replace existed working stack to history ones
            calculator.replaceStack(hisNumbers);
        }


    }

}
