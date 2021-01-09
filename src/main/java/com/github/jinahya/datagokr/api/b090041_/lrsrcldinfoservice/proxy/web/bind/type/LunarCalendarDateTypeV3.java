package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.JulianFields;
import java.util.Objects;

@Setter
@Getter
@Builder(access = AccessLevel.PACKAGE)
@Slf4j
public class LunarCalendarDateTypeV3 extends RepresentationModel<LunarCalendarDateTypeV3> {

    // -----------------------------------------------------------------------------------------------------------------
    @Getter
    @Builder(access = AccessLevel.PACKAGE)
    public static class Lunar {

        public static Lunar from(final Response.Body.Item item) {
            if (item == null) {
                return null;
            }
            return builder()
                    .year(item.getLunarYear())
                    .month(item.getLunarMonth())
                    .dayOfMonth(item.getLunarDayOfMonth())
                    .leapMonth(item.getLunarLeapMonth())
                    .ganzhiForYearHans(item.getLunarGanzhiForYearHans())
                    .ganzhiForYearKore(item.getLunarGanzhiForYearKore())
                    .ganzhiForMonthHans(item.getLunarGanzhiForMonthHans())
                    .ganzhiForMonthKore(item.getLunarGanzhiForMonthKore())
                    .ganzhiForDayOfMonthHans(item.getLunarGanzhiForDayOfMonthHans())
                    .ganzhiForDayOfMonthKore(item.getLunarGanzhiForDayOfMonthKore())
                    .build();
        }

        @Override
        public String toString() {
            return super.toString() + '{'
                   + "year=" + year
                   + ",month=" + month
                   + ",dayOfMonth=" + dayOfMonth
                   + ",leapMonth=" + leapMonth
                   + ",ganzhiForYearKore=" + ganzhiForYearKore
                   + ",ganzhiForYearHans=" + ganzhiForYearHans
                   + ",ganzhiForMonthKore=" + ganzhiForMonthKore
                   + ",ganzhiForMonthHans=" + ganzhiForMonthHans
                   + ",ganzhiForDayOfMonthKore=" + ganzhiForDayOfMonthKore
                   + ",ganzhiForDayOfMonthHans=" + ganzhiForDayOfMonthHans
                   + '}';
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            final Lunar casted = (Lunar) obj;
            return dayOfMonth == casted.dayOfMonth
                   && leapMonth == casted.leapMonth
                   && year.equals(casted.year)
                   && month == casted.month
                   && ganzhiForYearKore.equals(casted.ganzhiForYearKore)
                   && ganzhiForYearHans.equals(casted.ganzhiForYearHans)
                   && Objects.equals(ganzhiForMonthKore, casted.ganzhiForMonthKore)
                   && Objects.equals(ganzhiForMonthHans, casted.ganzhiForMonthHans)
                   && ganzhiForDayOfMonthKore.equals(casted.ganzhiForDayOfMonthKore)
                   && ganzhiForDayOfMonthHans.equals(casted.ganzhiForDayOfMonthHans);
        }

        @Override
        public int hashCode() {
            return Objects.hash(year, month, dayOfMonth, leapMonth, ganzhiForYearKore, ganzhiForYearHans,
                                ganzhiForMonthKore, ganzhiForMonthHans, ganzhiForDayOfMonthKore,
                                ganzhiForDayOfMonthHans);
        }

        @NotNull
        private final Year year;

        @NotNull
        private final Month month;

        @Max(30)
        @Min(1)
        private final int dayOfMonth;

        private final boolean leapMonth;

        @JsonProperty("세차")
        @NotBlank
        private final String ganzhiForYearKore;

        @JsonProperty("歲次")
        @NotBlank
        private final String ganzhiForYearHans;

        @JsonProperty("월건")
        @Nullable
        private final String ganzhiForMonthKore;

        @JsonProperty("月建")
        @Nullable
        private final String ganzhiForMonthHans;

        @JsonProperty("일진")
        @NotBlank
        private final String ganzhiForDayOfMonthKore;

        @JsonProperty("日辰")
        @NotBlank
        private final String ganzhiForDayOfMonthHans;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Getter
    @Builder(access = AccessLevel.PACKAGE)
    public static class Solar {

        public static Solar from(final Response.Body.Item item) {
            if (item == null) {
                return null;
            }
            return builder()
                    .date(item.getSolarDate())
                    .build();
        }

        @Override
        public String toString() {
            return super.toString() + '{'
                   + "date=" + date
                   + ",leapYear=" + isLeapYear()
                   + ",dayOfWeek=" + getDayOfWeek()
                   + ",julianDay=" + getJulianDay()
                   + '}';
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            final Solar casted = (Solar) obj;
            return date.equals(casted.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(date);
        }

        // -------------------------------------------------------------------------------------------------------------
        @JsonProperty
        public boolean isLeapYear() {
            Boolean result = leapYear;
            if (result == null) {
                leapYear = result = date.isLeapYear();
            }
            return result;
        }

        @JsonProperty
        public DayOfWeek getDayOfWeek() {
            DayOfWeek result = dayOfWeek;
            if (result == null) {
                dayOfWeek = result = date.getDayOfWeek();
            }
            return result;
        }

        @JsonProperty
        @PositiveOrZero
        public long getJulianDay() {
            Long result = julianDay;
            if (result == null) {
                julianDay = result = date.getLong(JulianFields.JULIAN_DAY);
            }
            return result;
        }

        @JsonProperty
        @NotNull
        private final LocalDate date;

        private volatile Boolean leapYear;

        private volatile DayOfWeek dayOfWeek;

        private volatile Long julianDay;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static LunarCalendarDateTypeV3 from(final Response.Body.Item item) {
        if (item == null) {
            return null;
        }
        return builder()
                .lunar(Lunar.from(item))
                .solar(Solar.from(item))
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() + '{'
               + "lunar=" + lunar
               + ",solar=" + solar
               + '}';
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        final LunarCalendarDateTypeV3 that = (LunarCalendarDateTypeV3) obj;
        return lunar.equals(that.lunar) && solar.equals(that.solar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lunar, solar);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Valid
    @NotNull
    private final Lunar lunar;

    @Valid
    @NotNull
    private final Solar solar;
}
