package com.andipangeran.reactive.billing.common;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
