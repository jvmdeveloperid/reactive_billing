package com.andipangeran.reactive.billing.api.order.cmd;

import com.andipangeran.reactive.billing.api.order.OrderId;
import com.andipangeran.reactive.billing.api.payment.PaymentId;
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
public class PaidOrder {

    private OrderId orderId;

    private PaymentId paymentId;

    private BigDecimal amount;

    private LocalDate paidDate;
}
