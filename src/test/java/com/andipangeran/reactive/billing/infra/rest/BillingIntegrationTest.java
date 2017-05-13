package com.andipangeran.reactive.billing.infra.rest;

import com.andipangeran.reactive.billing.api.order.OrderDto;
import com.andipangeran.reactive.billing.api.order.event.OrderInvoicedEvent;
import com.andipangeran.reactive.billing.api.subscriber.SubscriberDto;
import io.vavr.CheckedConsumer;
import io.vavr.control.Try;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static com.andipangeran.reactive.billing.infra.rest.OrderControllerIntegrationTest.createOrderCmd;
import static com.andipangeran.reactive.billing.infra.rest.OrderControllerIntegrationTest.createPaidOrderCmd;
import static com.andipangeran.reactive.billing.infra.rest.SubscriberControllerIntegrationTest.createSubscriberCmd;
import static junit.framework.TestCase.assertTrue;
import static org.awaitility.Awaitility.await;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class BillingIntegrationTest extends IntegrationRest {

    private static final String API_ORDER = "api/order";

    private static final String API_SUBSCRIBER = "api/subscriber";

    @Test
    public void createAndPaidOrder() throws Exception {

        Try<OrderDto> order = createSubscriber()
            .flatMap(this::createOrder)
            .andThenTry(this::paidOrder)
            .andThenTry(waitEvent(OrderInvoicedEvent.class));

        assertTrue(order.isSuccess());
    }

    private Try<SubscriberDto> createSubscriber() {
        return Try
            .of(() -> postAPI(API_SUBSCRIBER + "/create_subscriber", createSubscriberCmd(), SubscriberDto.class));
    }

    private Try<OrderDto> createOrder(SubscriberDto subscriber) {

        return Try
            .of(() -> postAPI(API_ORDER + "/create_order", createOrderCmd(subscriber.getSubscriberId()), OrderDto.class));
    }

    private void paidOrder(OrderDto order) {

        postAPI(API_ORDER + "/paid_order",
            createPaidOrderCmd(order.getOrderId(), order.getAmount())
        );
    }

    private CheckedConsumer<OrderDto> waitEvent(Class... events) {

        return orderDto -> {

            EventWaitter eventWaitter = new EventWaitter(events);
            this.eventBus.subscribe(eventWaitter);

            await()
                .atMost(20, TimeUnit.SECONDS)
                .until(() -> eventWaitter.getFuture().isDone());
        };
    }
}
