package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

@Entity
@Table(name = LunarCalendarDate.TABLE_NAME,
       uniqueConstraints = {
               @UniqueConstraint(columnNames = {
                       LunarCalendarDate.COLUMN_NAME_LUNAR_YEAR,
                       LunarCalendarDate.COLUMN_NAME_LUNAR_MONTH,
                       LunarCalendarDate.COLUMN_NAME_LUNAR_DAY,
                       LunarCalendarDate.COLUMN_NAME_LUNAR_LEAP_MONTH
               })
       }
)
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE)
@Slf4j
public class LunarCalendarDate {

    public static final String TABLE_NAME = "lunar_calendar_date";

    // ------------------------------------------------------------------------------------------ solar_date / solarDate
    public static final String COLUMN_NAME_SOLAR_DATE = "solar_date";

    public static final String ATTRIBUTE_NAE_SOLAR_DATE = "solarDate";

    // ------------------------------------------------------------------------------------------ lunar_year / lunarYear
    public static final String COLUMN_NAME_LUNAR_YEAR = "lunar_year";

    public static final String ATTRIBUTE_NAE_LUNAR_YEAR = "lunarYear";

    // ---------------------------------------------------------------------------------------- lunar_month / lunarMonth
    public static final String COLUMN_NAME_LUNAR_MONTH = "lunar_month";

    public static final String ATTRIBUTE_NAE_LUNAR_MONTH = "lunarMonth";

    // -------------------------------------------------------------------------------------------- lunar_day / lunarDay
    public static final String COLUMN_NAME_LUNAR_DAY = "lunar_day";

    public static final String ATTRIBUTE_NAE_LUNAR_DAY = "lunarDay";

    // ------------------------------------------------------------------------------------------------------- lunarDate
    public static final String ATTRIBUTE_NAME_LUNAR_DATE = "lunarDate";

    // ------------------------------------------------------------------------------- lunar_leap_month / lunarLeapMonth
    public static final String COLUMN_NAME_LUNAR_LEAP_MONTH = "lunar_leap_month";

    public static final String ATTRIBUTE_NAME_LUNAR_LEAP_MONTH = "lunarLeapMonth";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_LUNAR_GANZHI_YEAR = "lunar_ganzhi_year";

    public static final String ATTRIBUTE_NAME_LUNAR_GANZHI_YEAR = "lunarGanzhiYear";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_LUNAR_GANZHI_MONTH = "lunar_ganzhi_month";

    public static final String ATTRIBUTE_NAME_LUNAR_GANZHI_MONTH = "lunarGanzhiMonth";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_LUNAR_GANZHI_DAY = "lunar_ganzhi_day";

    public static final String ATTRIBUTE_NAME_LUNAR_GANZHI_DAY = "lunarGanzhiDay";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_MONTH_LUNAR = "month_lunar";

    public static final String ATTRIBUTE_NAME_MONTH_LUNAR = "monthLunar";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_MONTH_SOLAR = "month_solar";

    public static final String ATTRIBUTE_NAME_MONTH_SOLAR = "monthSolar";

    // -----------------------------------------------------------------------------------------------------------------
    public LunarCalendarDate() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + '{'
               + "solarDate=" + solarDate
               + ",lunarYear=" + lunarYear
               + ",lunarMonth=" + lunarMonth
               + ",lunarDay=" + lunarDay
               + ",lunarLeapMonth=" + lunarLeapMonth
               + ",lunarGanzhiYear=" + lunarGanzhiYear
               + ",lunarGanzhiMonth=" + lunarGanzhiMonth
               + ",lunarGanzhiDay=" + lunarGanzhiDay
               + ",groupLunar=" + monthLunar
               + ",groupSolar=" + monthSolar
               + '}';
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Id
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_SOLAR_DATE, nullable = false, insertable = true, updatable = false)
    private LocalDate solarDate;

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LUNAR_YEAR, nullable = false, insertable = true, updatable = false)
    private int lunarYear;

    @NotNull
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_NAME_LUNAR_MONTH, nullable = false, insertable = true, updatable = false)
    private Month lunarMonth;

    @Max(31)
    @Min(1)
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LUNAR_DAY, nullable = false, insertable = true, updatable = false)
    private int lunarDay;

    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LUNAR_LEAP_MONTH, nullable = false, insertable = true, updatable = false)
    private boolean lunarLeapMonth;

    @Size(min = 6, max = 6)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LUNAR_GANZHI_YEAR, nullable = false, insertable = true, updatable = false)
    private String lunarGanzhiYear;

    @Size(min = 6, max = 6)
    @Nullable
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_LUNAR_GANZHI_MONTH, nullable = true, insertable = true, updatable = false)
    private String lunarGanzhiMonth;

    @Size(min = 6, max = 6)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LUNAR_GANZHI_DAY, nullable = false, insertable = true, updatable = false)
    private String lunarGanzhiDay;

    // -----------------------------------------------------------------------------------------------------------------
    @Basic(optional = true)
    @Convert(converter = YearMonthAttributeConverter.class)
    @Column(name = COLUMN_NAME_MONTH_LUNAR, nullable = true, insertable = true, updatable = true)
    private YearMonth monthLunar;

    @Basic(optional = true)
    @Convert(converter = YearMonthAttributeConverter.class)
    @Column(name = COLUMN_NAME_MONTH_SOLAR, nullable = true, insertable = true, updatable = true)
    private YearMonth monthSolar;
}
