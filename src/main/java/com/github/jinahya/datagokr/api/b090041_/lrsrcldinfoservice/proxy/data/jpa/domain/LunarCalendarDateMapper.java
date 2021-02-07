package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Item;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Year;

import static java.util.Optional.ofNullable;

@Mapper(componentModel = "spring")
public interface LunarCalendarDateMapper {

    default Year map(final Integer year) {
        return ofNullable(year).map(Year::of).orElse(null);
    }

    /**
     * Maps from specified item to specified lunar calendar date.
     *
     * @param source the source item.
     * @param target the target lunar calendar date.
     * @return given {@code target}.
     */
    @Mapping(source = "lunarYearValue", target = LunarCalendarDate.ATTRIBUTE_NAME_LUNAR_YEAR)
    @Mapping(source = "lunarMonth", target = LunarCalendarDate.ATTRIBUTE_NAE_LUNAR_MONTH)
    @Mapping(source = "lunarDayOfMonth", target = LunarCalendarDate.ATTRIBUTE_NAE_LUNAR_DAY)
    @Mapping(source = "lunarLeapMonth", target = LunarCalendarDate.ATTRIBUTE_NAME_LUNAR_LEAP_MONTH)
    @Mapping(source = "lunSecha", target = LunarCalendarDate.ATTRIBUTE_NAME_LUNAR_GANZHI_YEAR)
    @Mapping(source = "lunWolgeon", target = LunarCalendarDate.ATTRIBUTE_NAME_LUNAR_GANZHI_MONTH)
    @Mapping(source = "lunIljin", target = LunarCalendarDate.ATTRIBUTE_NAME_LUNAR_GANZHI_DAY)
    @Mapping(source = "solarDate", target = LunarCalendarDate.ATTRIBUTE_NAE_SOLAR_DATE)
    LunarCalendarDate fromItem(Item source, @MappingTarget LunarCalendarDate target);

    default LunarCalendarDate fromItem(final Item source) {
        return fromItem(source, new LunarCalendarDate());
    }

    @InheritInverseConfiguration
    Item toItem(LunarCalendarDate source, @MappingTarget Item target);

    default Item toItem(LunarCalendarDate source) {
        return toItem(source, new Item());
    }
}
