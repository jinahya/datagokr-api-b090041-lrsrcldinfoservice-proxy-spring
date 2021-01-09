package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain;

import java.lang.reflect.Method;

public abstract class ToStringParseAttributeConverter<T> extends ToStringAttributeConverter<T> {

    protected ToStringParseAttributeConverter(final Class<T> attributeClass) {
        super(attributeClass);
    }

    @Override
    public T convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            final Method method = attributeClass.getMethod("parse", CharSequence.class);
            return attributeClass.cast(method.invoke(null, dbData));
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }
}
