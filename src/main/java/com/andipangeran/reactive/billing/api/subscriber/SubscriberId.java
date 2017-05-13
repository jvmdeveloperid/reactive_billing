package com.andipangeran.reactive.billing.api.subscriber;

import com.andipangeran.reactive.billing.common.BaseIdentifier;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class SubscriberId extends BaseIdentifier {

    @JsonCreator
    public SubscriberId(@JsonPropertyDescription(value = "id") String id) {
        super(id);
    }

    public SubscriberId() {
        super();
    }
}
