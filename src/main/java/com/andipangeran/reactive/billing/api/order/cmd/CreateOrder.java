package com.andipangeran.reactive.billing.api.order.cmd;

import com.andipangeran.reactive.billing.api.order.OrderId;
import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Value
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class CreateOrder {

    @NotNull
    private OrderId orderId;

    @NotNull
    private SubscriberId subscriberId;

    private LocalDate extendToDate;

    @NotNull
    private BigDecimal amount;
}
