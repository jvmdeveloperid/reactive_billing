package com.andipangeran.reactive.billing.api.subscriber.cmd;

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
public class ExtendSubscriber {

    private SubscriberId subscriberId;

    private LocalDate extendToDate;
}
