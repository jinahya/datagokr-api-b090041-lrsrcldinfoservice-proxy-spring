package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public abstract class StringAttributeConverter<T> implements AttributeConverter<T, String> {

    protected StringAttributeConverter(final Class<T> attributeClass) {
        super();
        this.attributeClass = Objects.requireNonNull(attributeClass, "attributeClass is null");
    }

    protected final Class<T> attributeClass;
}
