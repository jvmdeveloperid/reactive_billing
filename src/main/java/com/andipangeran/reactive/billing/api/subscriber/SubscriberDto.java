package com.andipangeran.reactive.billing.api.subscriber;

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
@EqualsAndHashCode
public class SubscriberDto {

    private SubscriberId subscriberId;

    private LocalDate expiredDate;
}
