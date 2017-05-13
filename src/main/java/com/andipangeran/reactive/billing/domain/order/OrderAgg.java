package com.andipangeran.reactive.billing.domain.order;

import com.andipangeran.reactive.billing.api.invoice.InvoiceId;
import com.andipangeran.reactive.billing.api.order.OrderDto;
import com.andipangeran.reactive.billing.api.order.OrderId;
import com.andipangeran.reactive.billing.api.order.cmd.MarkOrderPaidCompleted;
import com.andipangeran.reactive.billing.api.order.cmd.MarkOrderPaidFailed;
import com.andipangeran.reactive.billing.api.order.event.OrderCreatedEvent;
import com.andipangeran.reactive.billing.api.order.event.OrderFailEvent;
import com.andipangeran.reactive.billing.api.order.event.OrderInvoicedEvent;
import com.andipangeran.reactive.billing.api.order.event.OrderPaidEvent;
import com.andipangeran.reactive.billing.api.payment.PaymentId;
import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

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
public class OrderAgg {

    @AggregateIdentifier
    private OrderId orderId;

    private SubscriberId subscriberId;

    private LocalDate extendToDate;

    private BigDecimal amount;

    private BigDecimal paidAmount;

    private BigDecimal balance;

    private PaymentId paymentId;

    private InvoiceId invoiceId;

    private Status status;

    private LocalDate createdDate;

    private LocalDate paidDate;


    public OrderAgg(OrderId orderId, SubscriberId subscriberId, LocalDate extendToDate, BigDecimal amount) {
        apply(new OrderCreatedEvent(
            orderId,
            subscriberId,
            extendToDate,
            amount,
            amount,
            LocalDate.now()
        ));
    }

    public void paidOrder(PaymentId paymentId, BigDecimal paidAmount, LocalDate paidDate) {

        Assert.isTrue(this.amount.compareTo(paidAmount) == 0, "paid amount not equals order amount");

        apply(new OrderPaidEvent(
            this.orderId,
            this.subscriberId,
            this.extendToDate,
            this.amount,
            paymentId,
            paidAmount,
            paidDate
        ));
    }

    @CommandHandler
    public void paidFailed(MarkOrderPaidFailed cmd) {

        apply(new OrderFailEvent(this.orderId, "processing_paid", "", cmd));
    }


    @CommandHandler
    public void paidCompleted(MarkOrderPaidCompleted cmd) {

        apply(new OrderInvoicedEvent(this.orderId, cmd.getInvoiceId()));
    }

    @EventSourcingHandler
    public void onOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        this.orderId = orderCreatedEvent.getOrderId();
        this.subscriberId = orderCreatedEvent.getSubscriberId();
        this.extendToDate = orderCreatedEvent.getExtendToDate();
        this.amount = orderCreatedEvent.getAmount();
        this.balance = orderCreatedEvent.getBalance();
        this.createdDate = orderCreatedEvent.getCreatedDate();
        this.status = Status.CREATED;
    }

    @EventSourcingHandler
    public void onOrderPaid(OrderPaidEvent orderPaidEvent) {
        this.paymentId = orderPaidEvent.getPaymentId();
        this.paidAmount = orderPaidEvent.getPaidAmount();
        this.paidDate = orderPaidEvent.getPaidDate();
    }

    @EventSourcingHandler
    public void onInvoiced(OrderInvoicedEvent event) {
        this.invoiceId = event.getInvoiceId();
        this.status = Status.INVOICED;
    }

    public OrderDto getDto() {
        return new OrderDto(
            this.orderId,
            this.subscriberId,
            this.extendToDate,
            this.amount,
            this.paidAmount,
            this.balance,
            this.paymentId,
            this.invoiceId,
            this.createdDate,
            this.paidDate
        );
    }

    private enum Status {
        CREATED,
        INVOICED
    }
}
