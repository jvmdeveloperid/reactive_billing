package com.andipangeran.reactive.billing.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class FailEvent {

    private String action;

    private String reason;

    private Object request;
}
