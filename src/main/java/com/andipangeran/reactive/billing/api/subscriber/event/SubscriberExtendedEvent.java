package com.andipangeran.reactive.billing.api.subscriber.event;

import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

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
public class SubscriberExtendedEvent {

    private SubscriberId subscriberId;

    private LocalDate oldExpiredDate;

    private LocalDate newExpiredDate;
}
