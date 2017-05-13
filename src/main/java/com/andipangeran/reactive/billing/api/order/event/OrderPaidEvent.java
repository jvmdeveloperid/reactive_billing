package com.andipangeran.reactive.billing.api.order.event;

import com.andipangeran.reactive.billing.api.order.OrderId;
import com.andipangeran.reactive.billing.api.payment.PaymentId;
import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

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
public class OrderPaidEvent {

    private OrderId orderId;

    private SubscriberId subscriberId;

    private LocalDate extendToDate;

    private BigDecimal amount;

    private PaymentId paymentId;

    private BigDecimal paidAmount;

    private LocalDate paidDate;
}
