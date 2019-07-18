package com.airwallex.common;

/**
 * function interface for match concrete service
 */
public interface ServiceMatcher {

    /**
     * check whether match service by input
     *
     * @param input
     * @return
     */
    boolean match(String input);
}
