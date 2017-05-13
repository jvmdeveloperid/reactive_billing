package com.andipangeran.reactive.billing.infra.rest;

import com.andipangeran.reactive.billing.api.subscriber.SubscriberDto;
import com.andipangeran.reactive.billing.api.subscriber.SubscriberId;
import com.andipangeran.reactive.billing.api.subscriber.cmd.CreateSubscriber;
import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.assertNotNull;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class SubscriberControllerIntegrationTest extends IntegrationRest {

    private static final String API = "api/subscriber";

    @Test
    public void createSubscriber() throws Exception {

        SubscriberDto subscriber = postAPI(API + "/create_subscriber", createSubscriberCmd(), SubscriberDto.class);

        assertNotNull(subscriber);
    }

    public static CreateSubscriber createSubscriberCmd() {
        return new CreateSubscriber(
            new SubscriberId(),
            LocalDate.now()
        );
    }
}