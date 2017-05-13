package com.andipangeran.reactive.billing.api.subscriber.event;

import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import com.andipangeran.reactive.billing.common.FailEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SubscriberFailEvent extends FailEvent {

    private SubscriberId subscriberId;

    public SubscriberFailEvent(SubscriberId subscriberId, String action, String reason, Object request) {
        super(action, reason, request);
        this.subscriberId = subscriberId;
    }
}
