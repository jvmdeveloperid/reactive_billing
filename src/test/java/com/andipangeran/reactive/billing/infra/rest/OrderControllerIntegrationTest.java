package com.andipangeran.reactive.billing.infra.rest;

import com.andipangeran.reactive.billing.api.order.OrderDto;
import com.andipangeran.reactive.billing.api.order.OrderId;
import com.andipangeran.reactive.billing.api.order.cmd.CreateOrder;
import com.andipangeran.reactive.billing.api.order.cmd.PaidOrder;
import com.andipangeran.reactive.billing.api.payment.PaymentId;
import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static junit.framework.TestCase.assertNotNull;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class OrderControllerIntegrationTest extends IntegrationRest {

    private static final String ORDER_API = "api/order";

    @Test
    public void createOrder() throws Exception {

        OrderDto orderDto = postAPI(ORDER_API + "/create_order", createOrderCmd(new SubscriberId()), OrderDto.class);

        assertNotNull(orderDto);
    }

    public static CreateOrder createOrderCmd(SubscriberId subscriberId) {
        return new CreateOrder(
            new OrderId(),
            subscriberId,
            LocalDate.now(),
            BigDecimal.TEN
        );
    }

    public static PaidOrder createPaidOrderCmd(OrderId orderId, BigDecimal amount) {

        return new PaidOrder(
            orderId,
            new PaymentId(),
            amount,
            LocalDate.now()
        );
    }
}