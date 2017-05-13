package com.andipangeran.reactive.billing.api.invoice.cmd;

import com.andipangeran.reactive.billing.api.invoice.InvoiceId;
import com.andipangeran.reactive.billing.api.order.OrderId;
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
public class CreateInvoice {

    private InvoiceId invoiceId;

    private OrderId orderId;

    private SubscriberId subscriberId;

    private BigDecimal amount;

    private LocalDate createdDate;
}
