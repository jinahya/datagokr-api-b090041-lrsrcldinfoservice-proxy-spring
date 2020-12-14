package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * A class for <a href="https://en.wikipedia.org/wiki/Sexagenary_cycle">Sexagenary cycle</a>.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://gist.github.com/onacit/b06c3f33ece93939e55f379ac569f468">GitHub Gist</a>
 */
public final class 干支 { // 지지

    /**
     * Constants of <a href="https://en.wikipedia.org/wiki/Heavenly_Stems">Heavenly Stems</a>.
     */
    public enum 天干 { // 천간
        甲, // 갑
        乙, // 을
        丙, // 병
        丁, // 정
        戊, // 무
        己, // 기
        庚, // 경
        辛, // 신
        壬, // 임
        癸; // 계
    }

    /**
     * Constants of <a href="https://en.wikipedia.org/wiki/Earthly_Branches">Earthly Branches</a>.
     */
    public enum 地支 { // 지지
        子, // 자
        丑, // 축
        寅, // 인
        卯, // 묘
        辰, // 진
        巳, // 사
        午, // 오
        未, // 미
        申, // 신
        酉, // 유
        戌, // 술
        亥; // 해
    }

    // -----------------------------------------------------------------------------------------------------------------
    abstract static class Assigned<T extends TemporalAccessor> {

        Assigned(final T temporalAccessor, final 干支 干支) {
            super();
            this.temporalAccessor = requireNonNull(temporalAccessor, "temporalAccessor is null");
            this.干支 = 干支;
        }

        public 干支 get干支() {
            return 干支;
        }

        final T temporalAccessor;

        private final 干支 干支;
    }

    /**
     * Represents a 干支 assigned to specific year.
     */
    public static class 歲次 extends Assigned<Year> { // 세차

        public 歲次(final Year year, final 干支 干支) {
            super(year, requireNonNull(干支, "干支 is null"));
        }

        public Year getYear() {
            return temporalAccessor;
        }
    }

    /**
     * Represents a 干支 assigned to specific month of a year.
     */
    public static class 月建 extends Assigned<YearMonth> { // 월건

        public static 月建 of(final 歲次 歲次, final Month month, final 干支 干支) {
            final YearMonth yearMonth = YearMonth.of(歲次.getYear().getValue(), month);
            return new 月建(yearMonth, 干支);
        }

        public static 月建 ofLeapMonth(final 歲次 歲次, final Month month) {
            return of(歲次, month, null);
        }

        public 月建(final YearMonth yearMonth, final 干支 干支) {
            super(requireNonNull(yearMonth, "yearMonth is null"), 干支);
        }

        public YearMonth getYearMonth() {
            return temporalAccessor;
        }

        public boolean isLeapMonth() {
            return get干支() == null;
        }
    }

    /**
     * Represents a 干支 assigned to specific date.
     */
    public static class 日辰 extends Assigned<LocalDate> { // 일진

        public static 日辰 of(final 月建 月建, final int dayOfMonth, final 干支 干支) {
            final LocalDate localDate = LocalDate.of(
                    月建.getYearMonth().getYear(), 月建.getYearMonth().getMonth(), dayOfMonth);
            return new 日辰(localDate, 干支);
        }

        public 日辰(final LocalDate localDate, final 干支 干支) {
            super(requireNonNull(localDate, "localDate is null"), requireNonNull(干支, "干支 is null"));
        }

        public LocalDate getLocalDate() {
            return temporalAccessor;
        }
    }

    public static 干支 of(final String 干, final String 支) {
        requireNonNull(干, "干 is null");
        requireNonNull(支, "支 is null");
        return of(天干.valueOf(干), 地支.valueOf(支));
    }

    public static 干支 of(final String 干支) {
        requireNonNull(干支, "干支 is null");
        if (干支.length() != 2) {
            throw new IllegalArgumentException("干支(" +干支 + ").length(" + 干支.length() + ") != 2");
        }
        return of(干支.substring(0, 1), 干支.substring(1, 2));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable list of all values.
     */
    public static final List<干支> VALUES;

    static {
        final List<干支> l = new ArrayList<>();
        {
            final 天干[] 干们 = 天干.values();
            final 地支[] 支们 = 地支.values();
            final int lcm = 60;
            for (int i = 0, j = 0, k = 0; k++ < lcm; i = ++i % 干们.length, j = ++j % 支们.length) {
                l.add(new 干支(干们[i], 支们[j]));
            }
        }
        VALUES = Collections.unmodifiableList(l);
    }

    static int index(final 天干 干, final 地支 支) {
        return (干.ordinal() * 12 + 支.ordinal()) % 60;
    }

    /**
     * Returns the value of specified 天干 and 地支.
     *
     * @param 干 the 天干.
     * @param 支 the 地支.
     * @return the value of specified 天干 and 地支.
     */
    public static 干支 of(final 天干 干, final 地支 支) {
        requireNonNull(干, "干 is null");
        requireNonNull(支, "支 is null");
        return VALUES.get(index(干, 支));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified 天干 and 地支.
     *
     * @param 干 the 天干.
     * @param 支 the 地支.
     */
    private 干支(final 天干 干, final 地支 支) {
        super();
        this.干 = requireNonNull(干, "干 is null");
        this.支 = requireNonNull(支, "支 is null");
    }

    @Override
    public String toString() {
        return super.toString() + '{'
               + "干=" + 干
               + ",支=" + 支
               + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final 干支 casted = (干支) o;
        return 干 == casted.干 && 支 == casted.支;
    }

    @Override
    public int hashCode() {
        return Objects.hash(干, 支);
    }

    /**
     * Returns 天干 of this 干支.
     *
     * @return 天干 of this 干支.
     */
    public 天干 get干() {
        return 干;
    }

    /**
     * Returns 地支 of this 干支.
     *
     * @return 地支 of this 干支.
     */
    public 地支 get支() {
        return 支;
    }

    private final 天干 干;

    private final 地支 支;
}