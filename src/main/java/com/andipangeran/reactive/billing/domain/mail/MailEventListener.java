package com.andipangeran.reactive.billing.domain.mail;

import com.andipangeran.reactive.billing.api.invoice.event.InvoiceCreatedEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailEventListener {

    @NonNull
    private MailService mailService;

    @EventHandler
    public void onInvoiceCreated(InvoiceCreatedEvent invoiceCreatedEvent) {
        mailService.sendMail(invoiceCreatedEvent.getInvoiceId().toString());
    }
}
