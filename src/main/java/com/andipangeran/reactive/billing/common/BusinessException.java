package com.andipangeran.reactive.billing.common;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
