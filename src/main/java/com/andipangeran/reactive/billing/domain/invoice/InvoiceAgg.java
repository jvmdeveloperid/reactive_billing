package com.andipangeran.reactive.billing.domain.invoice;

import com.andipangeran.reactive.billing.api.invoice.InvoiceId;
import com.andipangeran.reactive.billing.api.invoice.event.InvoiceCreatedEvent;
import com.andipangeran.reactive.billing.api.order.OrderId;
import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Getter
@ToString
@Aggregate
@NoArgsConstructor
public class InvoiceAgg {

    @AggregateIdentifier
    private InvoiceId invoiceId;

    private OrderId orderId;

    private SubscriberId subscriberId;

    private BigDecimal amount;

    private LocalDate createdDate;

    public InvoiceAgg(InvoiceId invoiceId, OrderId orderId, SubscriberId subscriberId, BigDecimal amount, LocalDate createdDate) {
        apply(new InvoiceCreatedEvent(
            invoiceId,
            orderId,
            subscriberId,
            amount,
            createdDate
        ));
    }

    @EventSourcingHandler
    public void onInvoiceCreated(InvoiceCreatedEvent event) {
        this.invoiceId = event.getInvoiceId();
        this.orderId = event.getOrderId();
        this.subscriberId = event.getSubscriberId();
        this.amount = event.getAmount();
        this.createdDate = event.getCreatedDate();
    }
}
