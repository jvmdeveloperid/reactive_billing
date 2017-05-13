package com.andipangeran.reactive.billing.domain.invoice;

import com.andipangeran.reactive.billing.api.invoice.cmd.CreateInvoice;
import com.andipangeran.reactive.billing.api.invoice.event.InvoiceFailEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@RequiredArgsConstructor
public class InvoiceCommandHandler {

    @NonNull
    private Repository<InvoiceAgg> repository;

    @NonNull
    private EventBus eventBus;

    @CommandHandler
    public void createInvoice(CreateInvoice cmd) {

        try {
            repository.newInstance(
                () -> new InvoiceAgg(cmd.getInvoiceId(), cmd.getOrderId(), cmd.getSubscriberId(), cmd.getAmount(), cmd.getCreatedDate()));
        } catch (Exception e) {
            eventBus.publish(asEventMessage(new InvoiceFailEvent(
                cmd.getInvoiceId(),
                "create_invoice",
                e.getMessage(),
                cmd
            )));
        }
    }
}
