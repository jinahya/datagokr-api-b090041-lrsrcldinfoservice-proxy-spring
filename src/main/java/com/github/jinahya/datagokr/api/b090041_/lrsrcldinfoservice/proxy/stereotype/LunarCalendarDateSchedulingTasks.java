package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunarCalendarDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.TemporalAmount;

@Component
@RequiredArgsConstructor
@Slf4j
public class LunarCalendarDateSchedulingTasks {

    static final TemporalAmount PERIOD_FOR_PURGING_PAST_ENTRIES_BY_SOLAR_DATE = Period.ofYears(10);

    static final TemporalAmount PERIOD_FOR_PURGING_PAST_ENTRIES_BY_SOLAR_MONTH
            = Period.from(PERIOD_FOR_PURGING_PAST_ENTRIES_BY_SOLAR_DATE).minusYears(1L);

    static final TemporalAmount PERIOD_FOR_PURGING_FUTURE_ENTRIES_BY_SOLAR_DATE = Period.ofYears(10);

    static final TemporalAmount PERIOD_FOR_PURGING_FUTURE_ENTRIES_BY_SOLAR_MONTH
            = Period.from(PERIOD_FOR_PURGING_FUTURE_ENTRIES_BY_SOLAR_DATE).minusYears(1L);

    // -----------------------------------------------------------------------------------------------------------------
    @Scheduled(cron = "0 0 0 * * *")
    public void purgePastEntriesBySolarDate() {
        final int count = lunarCalendarDateRepository.deleteAllBySolarDateIsLessThanLimitNative(
                LocalDate.now().minus(PERIOD_FOR_PURGING_PAST_ENTRIES_BY_SOLAR_DATE));
        log.warn("number of deleted past entries by solar_date: {}", count);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void purgePastEntriesByMonthSolar() {
        final int count = lunarCalendarDateRepository.deleteAllByMonthSolarIsLessThanLimitNative(
                YearMonth.now().minus(PERIOD_FOR_PURGING_PAST_ENTRIES_BY_SOLAR_MONTH));
        log.info("number of deleted past entries by month_solar: {}", count);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Scheduled(cron = "0 0 0 * * *")
    public void purgeFutureEntriesBySolarDate() {
        final int count = lunarCalendarDateRepository.deleteAllBySolarDateIsLessThanLimitNative(
                LocalDate.now().plus(PERIOD_FOR_PURGING_FUTURE_ENTRIES_BY_SOLAR_DATE));
        log.info("number of deleted future entries by solar_date: {}", count);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void purgeFutureEntriesByMonthSolar() {
        final int count = lunarCalendarDateRepository.deleteAllByMonthSolarIsLessThanLimitNative(
                YearMonth.now().plus(PERIOD_FOR_PURGING_FUTURE_ENTRIES_BY_SOLAR_MONTH));
        log.info("number of deleted future entries by month_solar: {}", count);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private final LunarCalendarDateRepository lunarCalendarDateRepository;
}
