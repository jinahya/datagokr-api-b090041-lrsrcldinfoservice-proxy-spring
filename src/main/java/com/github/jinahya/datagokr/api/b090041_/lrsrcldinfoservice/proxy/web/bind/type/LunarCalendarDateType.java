package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind.type;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Setter
@Getter
@Slf4j
public class LunarCalendarDateType { //extends RepresentationModel<ItemType> {

    public static LunarCalendarDateType fromLunarCalendarDate(final LunarCalendarDate lunarCalendarDate) {
        requireNonNull(lunarCalendarDate, "lunarCalendarDate is null");
        final LunarCalendarDateType instance = new LunarCalendarDateType();
        instance.setSolarDate(lunarCalendarDate.getSolarDate());
        instance.setLunarDate(lunarCalendarDate.getLunarDate());
        instance.setSecha(lunarCalendarDate.getSecha());
        instance.setWolgeon(lunarCalendarDate.getWolgeon());
        instance.setIljin(lunarCalendarDate.getIljin());
        return instance;
    }

    // ------------------------------------------------------------------------------------------------- secha / 세차 / 歲次
    public String getSecha() {
        return secha;
    }

    public void setSecha(final String secha) {
        this.secha = secha;
    }

    public String get세차() {
        return ofNullable(getSecha()).map(v -> v.substring(0, 2)).orElse(null);
    }

    public String get歲次() {
        return ofNullable(getSecha()).map(v -> v.substring(3, 5)).orElse(null);
    }

    // ----------------------------------------------------------------------------------------------- wolgeon / 월건 / 月建
    public String getWolgeon() {
        return wolgeon;
    }

    public void setWolgeon(final String wolgeon) {
        this.wolgeon = wolgeon;
    }

    public String get월건() {
        return ofNullable(getWolgeon()).map(v -> v.substring(0, 2)).orElse(null);
    }

    public String get月建() {
        return ofNullable(getWolgeon()).map(v -> v.substring(3, 5)).orElse(null);
    }

    public boolean isLeapMonth() {
        return getWolgeon() == null;
    }

    // ------------------------------------------------------------------------------------------------- iljin / 일진 / 日辰
    public String getIljin() {
        return iljin;
    }

    public void setIljin(final String iljin) {
        this.iljin = iljin;
    }

    public String get일진() {
        return ofNullable(getIljin()).map(v -> v.substring(0, 2)).orElse(null);
    }

    public String get日辰() {
        return ofNullable(getIljin()).map(v -> v.substring(3, 5)).orElse(null);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    private LocalDate solarDate;

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    private LocalDate lunarDate;

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = 6, max = 6)
    @NotNull
    private String secha;

    @Size(min = 6, max = 6)
    @Nullable
    private String wolgeon;

    // -----------------------------------------------------------------------------------------------------------------
    private String 월건;

    private String 月建;

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = 6, max = 6)
    @NotNull
    private String iljin;

    private String 일진;

    private String 日辰;
}
