package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate.COLUMN_NAME_SOLAR_DATE;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate.COLUMN_NAME_SOLAR_MONTH_AGGREGATED;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate.TABLE_NAME;

/**
 * A repository interface for {@link LunarCalendarDate}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see LunarCalendarDateRepositoryAspect
 */
@Transactional
@Repository
public interface LunarCalendarDateRepository
        extends JpaRepository<LunarCalendarDate, LocalDate>, JpaSpecificationExecutor<LunarCalendarDate> {

    // -----------------------------------------------------------------------------------------------------------------
    Optional<LunarCalendarDate> findByLunarYearAndLunarMonthAndLunarDayAndLunarLeapMonth(
            int lunarYear, Month lunarMonth, int lunarDay, boolean lunarLeapMonth);

    List<LunarCalendarDate> findAllByLunarYearAndLunarMonth(int lunarYear, Month lunarMonth);

    default List<LunarCalendarDate> findAllByLunarYearAndLunarMonth(final YearMonth lunarMonth) {
        return findAllByLunarYearAndLunarMonth(lunarMonth.getYear(), Month.from(lunarMonth));
    }

    List<LunarCalendarDate> findAllByLunarYearAndLunarMonthAndLunarDay(int lunarYear, Month lunarMonth, int lunarDay);

    // -----------------------------------------------------------------------------------------------------------------
    List<LunarCalendarDate> findAllByLunarMonthAggregatedOrderByLunarYearAscLunarMonthAscLunarDayAsc(
            YearMonth lunarMonthAggregated);

    // -----------------------------------------------------------------------------------------------------------------
    List<LunarCalendarDate> findAllBySolarMonthAggregatedOrderBySolarDateAsc(YearMonth solarMonthAggregated);

    // -----------------------------------------------------------------------------------------------------------------
    int deleteAllBySolarDateIsLessThan(LocalDate solarDate);

    @Modifying
    @Query(value = "DELETE FROM " + TABLE_NAME
                   + " WHERE " + COLUMN_NAME_SOLAR_DATE + " < CURRENT_DATE"
                   + " AND " + COLUMN_NAME_SOLAR_DATE + " < :solarDate"
                   + " LIMIT 1000",
           nativeQuery = true)
    int deleteAllBySolarDateIsLessThanLimitNative(LocalDate solarDate);

    int deleteAllBySolarMonthAggregatedIsLessThan(LocalDate monthSolar);

    @Modifying
    @Query(value = "DELETE FROM " + TABLE_NAME
                   + " WHERE " + COLUMN_NAME_SOLAR_MONTH_AGGREGATED + " < FORMATDATETIME(CURRENT_DATE, 'yyyy-MM')"
                   + " AND " + COLUMN_NAME_SOLAR_MONTH_AGGREGATED + " < :monthSolar"
                   + " LIMIT 1000",
           nativeQuery = true)
    int deleteAllBySolarMonthAggregatedIsLessThanLimitNative(String monthSolar);

    default int deleteAllBySolarMonthAggregatedIsLessThanLimitNative(YearMonth monthSolar) {
        return deleteAllBySolarMonthAggregatedIsLessThanLimitNative(monthSolar.toString());
    }

    // -----------------------------------------------------------------------------------------------------------------
    int deleteAllBySolarDateIsGreaterThan(LocalDate solarDate);

    @Modifying
    @Query(value = "DELETE FROM " + TABLE_NAME
                   + " WHERE " + COLUMN_NAME_SOLAR_DATE + " > CURRENT_DATE"
                   + " AND " + COLUMN_NAME_SOLAR_DATE + " > :solarDate"
                   + " LIMIT 1000",
           nativeQuery = true)
    int deleteAllBySolarDateIsGreaterThanLimitNative(LocalDate solarDate);

    int deleteAllBySolarMonthAggregatedIsGreaterThan(LocalDate monthSolar);

    @Modifying
    @Query(value = "DELETE FROM " + TABLE_NAME
                   + " WHERE " + COLUMN_NAME_SOLAR_MONTH_AGGREGATED + " > FORMATDATETIME(CURRENT_DATE, 'yyyy-MM')"
                   + " AND " + COLUMN_NAME_SOLAR_MONTH_AGGREGATED + " > :monthSolar"
                   + " LIMIT 1000",
           nativeQuery = true)
    int deleteAllBySolarMonthAggregatedIsGreaterThanLimitNative(String monthSolar);

    default int deleteAllBySolarMonthAggregatedIsGreaterThanLimitNative(final YearMonth monthSolar) {
        return deleteAllBySolarMonthAggregatedIsGreaterThanLimitNative(monthSolar.toString());
    }
}
