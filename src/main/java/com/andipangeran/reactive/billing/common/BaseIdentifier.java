package com.andipangeran.reactive.billing.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.common.IdentifierFactory;

import java.io.Serializable;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
@Getter
@EqualsAndHashCode
@JsonSerialize(using = ToStringSerializer.class)
public class BaseIdentifier implements Serializable{

    private static final long serialVersionUID = 1L;
    private String identifier;

    public BaseIdentifier(String id) {

        this.identifier = PresentUtil.isPresent(id) ? id :  IdentifierFactory.getInstance().generateIdentifier();
    }

    public BaseIdentifier() {
        this.identifier = IdentifierFactory.getInstance().generateIdentifier();
    }

    @Override
    public String toString() {
        return this.getIdentifier();
    }
}
