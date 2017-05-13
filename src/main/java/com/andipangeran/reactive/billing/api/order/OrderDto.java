package com.andipangeran.reactive.billing.api.order;

import com.andipangeran.reactive.billing.api.invoice.InvoiceId;
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
@EqualsAndHashCode
@AllArgsConstructor
public class OrderDto {

    private OrderId orderId;

    private SubscriberId subscriberId;

    private LocalDate extendToDate;

    private BigDecimal amount;

    private BigDecimal paidAmount;

    private BigDecimal balance;

    private PaymentId paymentId;

    private InvoiceId invoiceId;

    private LocalDate createdDate;

    private LocalDate paidDate;
}
