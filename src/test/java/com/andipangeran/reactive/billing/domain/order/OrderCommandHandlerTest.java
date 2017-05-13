package com.andipangeran.reactive.billing.domain.order;

import com.andipangeran.reactive.billing.api.order.OrderId;
import com.andipangeran.reactive.billing.api.order.cmd.CreateOrder;
import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.JSR303ViolationException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class OrderCommandHandlerTest {

    private FixtureConfiguration<OrderAgg> testFixture;

    @Before
    public void setUp() throws Exception {
        testFixture = new AggregateTestFixture<>(OrderAgg.class);

        testFixture.registerAnnotatedCommandHandler(new OrderCommandHandler(testFixture.getRepository(), testFixture.getEventBus()));
        testFixture.registerCommandDispatchInterceptor(new BeanValidationInterceptor<>());
    }

}