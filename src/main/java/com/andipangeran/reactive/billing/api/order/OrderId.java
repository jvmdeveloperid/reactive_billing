package com.andipangeran.reactive.billing.api.order;

import com.andipangeran.reactive.billing.common.BaseIdentifier;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class OrderId extends BaseIdentifier {

    @JsonCreator
    public OrderId(@JsonPropertyDescription(value = "id") String id) {
        super(id);
    }

    public OrderId() {
        super();
    }
}
