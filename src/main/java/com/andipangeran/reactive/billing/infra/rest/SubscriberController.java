package com.andipangeran.reactive.billing.infra.rest;

import com.andipangeran.reactive.billing.api.subscriber.SubscriberDto;
import com.andipangeran.reactive.billing.api.subscriber.cmd.CreateSubscriber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.andipangeran.reactive.billing.infra.rest.RestConstant.V1_0;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@RestController
@RequestMapping(value = "api/subscriber", consumes = V1_0, produces = V1_0)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriberController {

    @NonNull
    private final CommandGateway commandGateway;

    @RequestMapping(value = "/create_subscriber", method = RequestMethod.POST)
    public ResponseEntity<SubscriberDto> createSubscriber(@RequestBody CreateSubscriber createSubscriber) {

        SubscriberDto dto = commandGateway.sendAndWait(createSubscriber);

        return ResponseEntity.ok(dto);
    }
}
