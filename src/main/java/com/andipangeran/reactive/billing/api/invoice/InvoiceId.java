package com.andipangeran.reactive.billing.api.invoice;

import com.andipangeran.reactive.billing.common.BaseIdentifier;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.ToString;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class InvoiceId extends BaseIdentifier {

    @JsonCreator
    public InvoiceId(@JsonPropertyDescription(value = "id") String id) {
        super(id);
    }

    public InvoiceId() {
        super();
    }
}
