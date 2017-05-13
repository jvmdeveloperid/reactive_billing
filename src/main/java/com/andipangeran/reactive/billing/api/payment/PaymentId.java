package com.andipangeran.reactive.billing.api.payment;

import com.andipangeran.reactive.billing.common.BaseIdentifier;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class PaymentId extends BaseIdentifier {

    @JsonCreator
    public PaymentId(@JsonPropertyDescription(value = "id") String id) {
        super(id);
    }

    public PaymentId() {
        super();
    }
}
