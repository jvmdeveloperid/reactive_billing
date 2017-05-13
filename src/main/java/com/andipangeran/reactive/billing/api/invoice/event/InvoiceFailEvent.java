package com.andipangeran.reactive.billing.api.invoice.event;

import com.andipangeran.reactive.billing.api.invoice.InvoiceId;
import com.andipangeran.reactive.billing.common.FailEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class InvoiceFailEvent extends FailEvent {

    private InvoiceId invoiceId;

    public InvoiceFailEvent(InvoiceId invoiceId, String action, String reason, Object request) {
        super(action, reason, request);
        this.invoiceId = invoiceId;
    }
}
