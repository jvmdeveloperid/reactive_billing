package com.andipangeran.reactive.billing.domain.management;

import com.andipangeran.reactive.billing.api.invoice.InvoiceId;
import com.andipangeran.reactive.billing.api.invoice.cmd.CreateInvoice;
import com.andipangeran.reactive.billing.api.invoice.event.InvoiceCreatedEvent;
import com.andipangeran.reactive.billing.api.invoice.event.InvoiceFailEvent;
import com.andipangeran.reactive.billing.api.order.OrderId;
import com.andipangeran.reactive.billing.api.order.cmd.MarkOrderPaidCompleted;
import com.andipangeran.reactive.billing.api.order.cmd.MarkOrderPaidFailed;
import com.andipangeran.reactive.billing.api.order.event.OrderPaidEvent;
import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import com.andipangeran.reactive.billing.api.subscriber.cmd.ExtendSubscriber;
import com.andipangeran.reactive.billing.api.subscriber.event.SubscriberExtendedEvent;
import com.andipangeran.reactive.billing.api.subscriber.event.SubscriberFailEvent;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;
import static org.axonframework.eventhandling.saga.SagaLifecycle.associateWith;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Saga
@NoArgsConstructor
@Slf4j
public class OrderPaidSaga {

    @Autowired
    private transient CommandBus commandBus;

    private OrderId orderId;

    private CreateInvoice invoiceCmd;

    private ExtendSubscriber extendSubscriber;

    private Tuple2<Boolean, Boolean> invoiced = Tuple.of(Boolean.FALSE, Boolean.FALSE);

    private Tuple2<Boolean, Boolean> subscribed = Tuple.of(Boolean.FALSE, Boolean.FALSE);

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void onOrderPaidEvent(OrderPaidEvent event) {

        log.debug("onOrderPaidEvent : {}", event);

        orderId = event.getOrderId();

        //send create invoice
        InvoiceId invoiceId = new InvoiceId();
        associateWith("invoiceId", invoiceId.toString());

        invoiceCmd = new CreateInvoice(invoiceId, event.getOrderId(), event.getSubscriberId(), event.getAmount(), event.getPaidDate());
        commandBus.dispatch(asCommandMessage(invoiceCmd), LoggingCallback.INSTANCE);


        //send extend subscription
        SubscriberId subscriberId = event.getSubscriberId();
        associateWith("subscriberId", subscriberId.toString());

        extendSubscriber = new ExtendSubscriber(event.getSubscriberId(), event.getExtendToDate());
        commandBus.dispatch(asCommandMessage(extendSubscriber), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "invoiceId")
    public void onInvoiceCreated(InvoiceCreatedEvent event) {

        log.debug("onInvoiceCreated : {}", event);

        invoiced = Tuple.of(true, true);
        processDone();
    }

    @SagaEventHandler(associationProperty = "invoiceId")
    public void onInvoiceFail(InvoiceFailEvent event) {

        log.debug("onInvoiceFail : {}", event);

        invoiced = Tuple.of(true, false);
        processDone();
    }

    @SagaEventHandler(associationProperty = "subscriberId")
    public void onSubscriberExtended(SubscriberExtendedEvent event) {

        log.debug("onSubscriberExtended : {}", event);

        subscribed = Tuple.of(true, true);
        processDone();
    }

    @SagaEventHandler(associationProperty = "subscriberId")
    public void onSubscriberFail(SubscriberFailEvent event) {

        log.debug("onSubscriberFail : {}", event);

        subscribed = Tuple.of(true, false);
        processDone();
    }

    private void processDone() {

        log.debug("invoiced : {} || subscribed : {}", invoiced, subscribed);

        boolean isDone = invoiced._1 && subscribed._1;

        boolean isAllOk = invoiced._2 && subscribed._2;

        if (isDone) {

            if (isAllOk) {

                onDoneAndAllSuccess();
            } else {

                onDoneAndHaveFail();
            }
        }
    }

    private void onDoneAndAllSuccess() {

        MarkOrderPaidCompleted cmd = new MarkOrderPaidCompleted(this.orderId, invoiceCmd.getInvoiceId());

        commandBus.dispatch(asCommandMessage(cmd), LoggingCallback.INSTANCE);

        SagaLifecycle.end();
    }

    private void onDoneAndHaveFail() {

        //TODO compensation action on invoice fail but extend subscriber success
        if ((!invoiced._2) && subscribed._2) {


        }

        //TODO compensation action on invoice success but extend subscriber fail
        if (invoiced._2 && (!subscribed._2)) {
        }

        MarkOrderPaidFailed cmd = new MarkOrderPaidFailed(orderId);

        commandBus.dispatch(asCommandMessage(cmd), LoggingCallback.INSTANCE);

        SagaLifecycle.end();
    }


}
