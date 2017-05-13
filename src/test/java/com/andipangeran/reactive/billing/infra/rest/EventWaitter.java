package com.andipangeran.reactive.billing.infra.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Slf4j
public class EventWaitter implements Consumer<List<? extends EventMessage<?>>> {

    private Set<String> interest;

    private CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();

    public EventWaitter(Class... events) {
        this.interest = Arrays
            .stream(events)
            .map(types -> types.getSimpleName())
            .collect(Collectors.toSet());
    }

    @Override
    public void accept(List<? extends EventMessage<?>> eventMessages) {

        eventMessages
            .stream()
            .map(event -> event.getPayloadType().getSimpleName())
            .map(event -> {
                log.debug("event wait: "+event);
                return event;
            })
            .filter(str -> interest.contains(str))
            .findAny()
            .ifPresent(item -> {

                log.debug("event wait: "+item);
                future.complete(true);
            });
    }
}
