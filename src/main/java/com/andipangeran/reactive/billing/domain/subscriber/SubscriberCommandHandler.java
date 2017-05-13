package com.andipangeran.reactive.billing.domain.subscriber;

import com.andipangeran.reactive.billing.api.subscriber.SubscriberDto;
import com.andipangeran.reactive.billing.api.subscriber.cmd.CreateSubscriber;
import com.andipangeran.reactive.billing.api.subscriber.cmd.ExtendSubscriber;
import com.andipangeran.reactive.billing.api.subscriber.event.SubscriberFailEvent;
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
public class SubscriberCommandHandler {

    @NonNull
    private Repository<SubscriberAgg> repository;

    @NonNull
    private EventBus eventBus;

    @CommandHandler
    public SubscriberDto createSubscriber(CreateSubscriber cmd) {

        try {
            Aggregate<SubscriberAgg> agg = repository
                .newInstance(() -> new SubscriberAgg(cmd.getSubscriberId(), cmd.getExpiredDate()));

            return agg.invoke(SubscriberAgg::getDto);
        } catch (Exception e) {
            eventBus.publish(asEventMessage(new SubscriberFailEvent(
                cmd.getSubscriberId(),
                "create_subscriber",
                e.getMessage(),
                cmd
            )));
            return null;
        }
    }

    @CommandHandler
    public void extend(ExtendSubscriber cmd) {

        try {
            Aggregate<SubscriberAgg> agg = repository.load(cmd.getSubscriberId().toString());

            agg.execute(sub -> sub.extendSubscriber(cmd.getExtendToDate()));
        } catch (AggregateNotFoundException ex) {

            eventBus.publish(asEventMessage(new SubscriberFailEvent(
                cmd.getSubscriberId(),
                "load_subscriber",
                ex.getMessage(),
                cmd
            )));
        } catch (IllegalArgumentException ex) {

            eventBus.publish(asEventMessage(new SubscriberFailEvent(
                cmd.getSubscriberId(),
                "extend_subscriber",
                ex.getMessage(),
                cmd
            )));
        }
    }

}
