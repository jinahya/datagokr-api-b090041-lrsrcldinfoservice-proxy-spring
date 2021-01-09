package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain;

public abstract class ToStringAttributeConverter<T> extends StringAttributeConverter<T> {

    protected ToStringAttributeConverter(final Class<T> attributeClass) {
        super(attributeClass);
    }

    @Override
    public String convertToDatabaseColumn(final T attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }
}
