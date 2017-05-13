package com.andipangeran.reactive.billing.configuration;

import com.andipangeran.reactive.billing.domain.invoice.InvoiceAgg;
import com.andipangeran.reactive.billing.domain.invoice.InvoiceCommandHandler;
import com.andipangeran.reactive.billing.domain.management.OrderPaidSaga;
import com.andipangeran.reactive.billing.domain.order.OrderAgg;
import com.andipangeran.reactive.billing.domain.order.OrderCommandHandler;
import com.andipangeran.reactive.billing.domain.subscriber.SubscriberAgg;
import com.andipangeran.reactive.billing.domain.subscriber.SubscriberCommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.spring.config.AxonConfiguration;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Configuration
@Slf4j
public class AxonConfig {

    @Autowired
    private AxonConfiguration axonConfiguration;

    @Autowired
    private EventBus eventBus;

    @Autowired
    public void configure(@Qualifier("localSegment") SimpleCommandBus simpleCommandBus) {
        simpleCommandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
    }

    @Bean
    public InvoiceCommandHandler invoiceCommandHandler() {
        log.debug("create invoiceCommandHandler");
        return new InvoiceCommandHandler(axonConfiguration.repository(InvoiceAgg.class), eventBus);
    }

    @Bean
    public OrderCommandHandler orderCommandHandler() {
        log.debug("create orderCommandHandler");
        return new OrderCommandHandler(axonConfiguration.repository(OrderAgg.class), eventBus);
    }

    @Bean
    public SubscriberCommandHandler subscriberCommandHandler() {
        log.debug("create subscriberCommandHandler");
        return new SubscriberCommandHandler(axonConfiguration.repository(SubscriberAgg.class), eventBus);
    }


    @Bean
    public SagaConfiguration orderPaidSagaConfiguration() {
        return SagaConfiguration.trackingSagaManager(OrderPaidSaga.class);
    }
}
