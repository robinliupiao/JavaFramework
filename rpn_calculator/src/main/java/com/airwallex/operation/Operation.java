package com.airwallex.operation;

import com.airwallex.calculator.Calculator;
import com.airwallex.common.ServiceMatcher;
import com.airwallex.exception.OperationException;

/**
 * the basic operation interface
 */
public interface Operation extends ServiceMatcher {

    /**
     * get string representation of operator
     *
     * @return
     */
    String getRepresentation();


    /**
     * concrete operation logic
     * @param calculator
     * @throws OperationException
     */
    void operate(Calculator calculator) throws OperationException;



    @Override
    default boolean match(String representation) {
        return getRepresentation().equalsIgnoreCase(representation);
    }


}
