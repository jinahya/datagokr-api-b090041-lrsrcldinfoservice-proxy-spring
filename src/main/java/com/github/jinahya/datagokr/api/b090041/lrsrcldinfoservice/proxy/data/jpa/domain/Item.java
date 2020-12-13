package com.github.jinahya.datagokr.api.b090041.lrsrcldinfoservice.proxy.data.jpa.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@Slf4j
public class Item {

    public boolean isLeapMonth() {
        return 月建 == null;
    }

    @NotNull
    @Id
    @Column(name = "solar_date", nullable = false, insertable = true, updatable = false)
    private LocalDate solarDate;

    @NotNull
    @Column(name="lunar_date", nullable = false, insertable = true, updatable = false, unique = true)
    private LocalDate lunarDate;

    @NotNull
    @Column(name = "歲次", nullable = false, insertable = true, updatable = false)
    private 干支.歲次 歲次; // 세차

    @Nullable
    @Column(name = "月建", nullable = false, insertable = true, updatable = false)
    private 干支.月建 月建; // 월건

    @NotNull
    @Column(name = "日辰", nullable = false, insertable = true, updatable = false)
    private 干支.日辰 日辰; // 일진
}
