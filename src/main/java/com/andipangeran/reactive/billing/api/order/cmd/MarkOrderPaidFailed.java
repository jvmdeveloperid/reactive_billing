package com.andipangeran.reactive.billing.api.order.cmd;

import com.andipangeran.reactive.billing.api.order.OrderId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Value
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class MarkOrderPaidFailed {

    @TargetAggregateIdentifier
    private OrderId orderId;
}
