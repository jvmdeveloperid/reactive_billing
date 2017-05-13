package com.andipangeran.reactive.billing.infra.rest;

import com.andipangeran.reactive.billing.api.order.OrderDto;
import com.andipangeran.reactive.billing.api.order.cmd.CreateOrder;
import com.andipangeran.reactive.billing.api.order.cmd.PaidOrder;
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
@RequestMapping(value = "api/order", consumes = V1_0, produces = V1_0)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    @NonNull
    private final CommandGateway commandGateway;

    @RequestMapping(value = "/create_order", method = RequestMethod.POST)
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrder createOrder) {

        OrderDto orderDto = commandGateway.sendAndWait(createOrder);

        return ResponseEntity.ok(orderDto);
    }

    @RequestMapping(value = "/paid_order", method = RequestMethod.POST)
    public void paidOrder(@RequestBody PaidOrder paidOrder) {

        commandGateway.send(paidOrder);
    }
}
