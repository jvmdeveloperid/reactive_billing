package com.andipangeran.reactive.billing.api.order.event;

import com.andipangeran.reactive.billing.api.order.OrderId;
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
public class OrderFailEvent extends FailEvent {

    private OrderId orderId;

    public OrderFailEvent(OrderId orderId, String action, String reason, Object request) {
        super(action, reason, request);
        this.orderId = orderId;
    }
}
