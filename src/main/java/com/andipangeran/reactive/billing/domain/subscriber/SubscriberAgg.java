package com.andipangeran.reactive.billing.domain.subscriber;

import com.andipangeran.reactive.billing.api.subscriber.SubscriberDto;
import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import com.andipangeran.reactive.billing.api.subscriber.event.SubscriberCreatedEvent;
import com.andipangeran.reactive.billing.api.subscriber.event.SubscriberExtendedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

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
public class SubscriberAgg {

    @AggregateIdentifier
    private SubscriberId subscriberId;

    private LocalDate expiredDate;

    public SubscriberAgg(SubscriberId subscriberId, LocalDate expiredDate) {
        apply(new SubscriberCreatedEvent(
            subscriberId,
            expiredDate
        ));
    }

    public void extendSubscriber(LocalDate expiredDate) {
        apply(new SubscriberExtendedEvent(
            subscriberId,
            this.expiredDate,
            expiredDate
        ));
    }

    @EventSourcingHandler
    public void onSubscriberCreated(SubscriberCreatedEvent event) {
        this.subscriberId = event.getSubscriberId();
        this.expiredDate = event.getExpiredDate();
    }

    @EventSourcingHandler
    public void onSubscriberExtendEvent(SubscriberExtendedEvent event) {
        this.expiredDate = event.getNewExpiredDate();
    }

    public SubscriberDto getDto() {
        return new SubscriberDto(
            this.getSubscriberId(),
            this.getExpiredDate()
        );
    }
}
