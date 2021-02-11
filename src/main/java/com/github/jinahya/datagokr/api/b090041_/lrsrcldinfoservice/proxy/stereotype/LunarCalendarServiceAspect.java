package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Item;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDateMapper;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunarCalendarDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LunarCalendarServiceAspect {

    // -----------------------------------------------------------------------------------------------------------------
    @SuppressWarnings({"unchecked"})
    @Around(value = "execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarCalendarService.getItemsForLunarDate(..))"
                    + " && args(lunarYear,lunarMonth,lunarDayOfMonth)",
            argNames = "joinPoint,lunarYear,lunarMonth,lunarDayOfMonth")
    public Object aroundGetByLunarDate(final ProceedingJoinPoint joinPoint, final Year lunarYear,
                                       final Month lunarMonth, final int lunarDayOfMonth)
            throws Throwable {
        return Optional.of(lunarCalendarDateRepository.findAllByLunarYearAndLunarMonthAndLunarDay(
                lunarYear.getValue(), lunarMonth, lunarDayOfMonth))
                .filter(l -> !l.isEmpty())
                .map(l -> l.stream().map(lunarCalendarDateMapper::toItem).collect(toList()))
                .orElseGet(() -> {
                    final List<Item> items;
                    try {
                        items = (List<Item>) joinPoint.proceed();
                    } catch (final Throwable t) {
                        throw new RuntimeException(t);
                    }
                    final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        items.stream()
                                .map(lunarCalendarDateMapper::fromItem)
                                .forEach(lunarCalendarDateRepository::save);
                    });
                    return items;
                });
    }

    @Around("execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarCalendarService.getItemsForLunarYearMonth(..))"
            + " && args(lunarYearMonth)")
    @SuppressWarnings({"unchecked"})
    public Object aroundGetByLunarDate(final ProceedingJoinPoint joinPoint, final YearMonth lunarYearMonth)
            throws Throwable {
        return Optional.of(lunarCalendarDateRepository.findAllByLunarYearAndLunarMonth(lunarYearMonth))
                .filter(l -> !l.isEmpty())
                .map(l -> l.stream().map(lunarCalendarDateMapper::toItem).collect(toList()))
                .orElseGet(() -> {
                    final List<Item> items;
                    try {
                        items = (List<Item>) joinPoint.proceed();
                    } catch (final Throwable t) {
                        throw new RuntimeException(t);
                    }
                    final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        items.stream()
                                .map(lunarCalendarDateMapper::fromItem)
                                .peek(d -> d.setLunarMonthAggregated(lunarYearMonth))
                                .forEach(lunarCalendarDateRepository::save);
                    });
                    return items;
                });
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Around("execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarCalendarService.getItemsForSolarDate(..))"
            + " && args(solarDate)")
    public Object aroundGetItemsForSolarDate(final ProceedingJoinPoint joinPoint, final LocalDate solarDate)
            throws Throwable {
        return lunarCalendarDateRepository.findById(solarDate)
                .map(lunarCalendarDateMapper::toItem)
                .map(v -> {
                    final List<Item> dates = new ArrayList<>();
                    dates.add(v);
                    return dates;
                })
                .orElseGet(() -> {
                    final List<Item> items;
                    try {
                        items = (List<Item>) joinPoint.proceed();
                    } catch (final Throwable t) {
                        throw new RuntimeException(t);
                    }
                    final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        items.stream()
                                .map(lunarCalendarDateMapper::fromItem)
                                .forEach(lunarCalendarDateRepository::save);
                    });
                    return items;
                });
    }

    @Around("execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarCalendarService.getItemsForSolarYearMonth(..))"
            + " && args(solarYearMonth)")
    @SuppressWarnings({"unchecked"})
    public Object aroundGetItemsForSolarYearMonth(final ProceedingJoinPoint joinPoint, final YearMonth solarYearMonth)
            throws Throwable {
        return Optional.of(lunarCalendarDateRepository.findAllBySolarMonthAggregatedOrderBySolarDateAsc(solarYearMonth))
                .filter(l -> !l.isEmpty())
                .map(l -> l.stream().map(lunarCalendarDateMapper::toItem).collect(toList()))
                .orElseGet(() -> {
                    final List<Item> items;
                    try {
                        items = (List<Item>) joinPoint.proceed();
                    } catch (final Throwable t) {
                        throw new RuntimeException(t);
                    }
                    final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        items.stream().map(lunarCalendarDateMapper::fromItem)
                                .peek(d -> d.setSolarMonthAggregated(solarYearMonth))
                                .forEach(lunarCalendarDateRepository::save);
                    });
                    return items;
                });
    }

    // -----------------------------------------------------------------------------------------------------------------
    private final LunarCalendarDateRepository lunarCalendarDateRepository;

    private final LunarCalendarDateMapper lunarCalendarDateMapper;
}
