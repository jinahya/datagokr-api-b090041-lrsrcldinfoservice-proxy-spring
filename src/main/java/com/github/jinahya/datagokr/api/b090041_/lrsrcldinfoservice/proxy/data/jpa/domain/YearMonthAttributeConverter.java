package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain;

import java.time.YearMonth;

public class YearMonthAttributeConverter extends ToStringParseAttributeConverter<YearMonth> {

    protected YearMonthAttributeConverter() {
        super(YearMonth.class);
    }

    @Override
    public YearMonth convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return null;
        }
        return YearMonth.parse(dbData);
    }
}
