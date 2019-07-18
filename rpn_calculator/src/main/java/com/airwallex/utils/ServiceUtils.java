package com.airwallex.utils;

import com.airwallex.common.ServiceMatcher;
import com.airwallex.display.DisplayStrategy;
import com.airwallex.operation.Operation;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * service utils, which can load service by serviceLoader system
 */
public class ServiceUtils {

    private static ServiceLoader<Operation> operationLoader;
    private static ServiceLoader<DisplayStrategy> displayLoader;

    static {
        operationLoader = ServiceLoader.load(Operation.class);
        displayLoader = ServiceLoader.load(DisplayStrategy.class);
    }

    public static DisplayStrategy getDisplayStrategy(String name) {
        return (DisplayStrategy) getService(displayLoader, name);
    }

    public static Operation getOperation(String operator) {
        return (Operation) getService(operationLoader, operator);
    }

    public static Map<String, Operation> getOperationMap() {
        List<?> list = getServiceList(operationLoader);
        return list.stream()
                .map(item -> (Operation) item)
                .collect(Collectors.toMap(
                        item -> item.getRepresentation().toLowerCase(),
                        item -> item
                ));
    }


    public static ServiceMatcher getService(ServiceLoader<? extends ServiceMatcher> loader, String input) {
        for (ServiceMatcher service : loader) {
            if (service.match(input)) {
                return service;
            }
        }
        return null;
    }

    private static List getServiceList(ServiceLoader<? extends ServiceMatcher> loader) {
        return Lists.newArrayList(loader);
    }

}
