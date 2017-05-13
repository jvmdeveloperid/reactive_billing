package com.andipangeran.reactive.billing.domain.order;

import com.andipangeran.reactive.billing.api.order.OrderDto;
import com.andipangeran.reactive.billing.api.order.cmd.CreateOrder;
import com.andipangeran.reactive.billing.api.order.cmd.PaidOrder;
import com.andipangeran.reactive.billing.api.order.event.OrderFailEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@RequiredArgsConstructor
public class OrderCommandHandler {

    @NonNull
    private Repository<OrderAgg> repository;

    @NonNull
    private EventBus eventBus;

    @CommandHandler
    public OrderDto createOrder(CreateOrder cmd) {

        try {
            Aggregate<OrderAgg> agg = repository
                .newInstance(() -> new OrderAgg(cmd.getOrderId(), cmd.getSubscriberId(), cmd.getExtendToDate(), cmd.getAmount()));


            return agg.invoke(OrderAgg::getDto);

        } catch (Exception e) {
            eventBus.publish(asEventMessage(new OrderFailEvent(
                cmd.getOrderId(),
                "create_order",
                e.getMessage(),
                cmd
            )));

            return null;
        }
    }

    @CommandHandler
    public void paidOrder(PaidOrder cmd) {

        try {
            Aggregate<OrderAgg> agg = repository.load(cmd.getOrderId().toString());

            agg.execute(order -> order.paidOrder(cmd.getPaymentId(), cmd.getAmount(), cmd.getPaidDate()));
        } catch (AggregateNotFoundException ex) {

            eventBus.publish(asEventMessage(new OrderFailEvent(
                cmd.getOrderId(),
                "load_order",
                ex.getMessage(),
                cmd
            )));
        } catch (IllegalArgumentException ex) {

            eventBus.publish(asEventMessage(new OrderFailEvent(
                cmd.getOrderId(),
                "paid_order",
                ex.getMessage(),
                cmd
            )));
        }
    }

}
