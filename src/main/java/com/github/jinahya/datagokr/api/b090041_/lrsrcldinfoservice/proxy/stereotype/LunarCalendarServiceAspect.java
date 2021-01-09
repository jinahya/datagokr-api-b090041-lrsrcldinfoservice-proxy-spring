package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
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
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LunarCalendarServiceAspect {

    // -----------------------------------------------------------------------------------------------------------------
    @Around("execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarCalendarService.getItemsForSolarDate(..))"
            + " && args(solarDate)")
    public Object aroundGetItemsForSolarDate(final ProceedingJoinPoint joinPoint, final LocalDate solarDate)
            throws Throwable {
        return lunarCalendarDateRepository.findById(solarDate)
                .map(LunarCalendarDate::toItem)
                .map(v -> {
                    final List<Response.Body.Item> dates = new ArrayList<>();
                    dates.add(v);
                    return dates;
                })
                .orElseGet(() -> {
                    final List<Response.Body.Item> items;
                    try {
                        items = (List<Response.Body.Item>) joinPoint.proceed();
                    } catch (final Throwable t) {
                        throw new RuntimeException(t);
                    }
                    final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        items.stream().map(LunarCalendarDate::from).forEach(lunarCalendarDateRepository::save);
                    });
                    return items;
                });
    }

    @Around("execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarCalendarService.getItemsForSolarYearMonth(..))"
            + " && args(solarYearMonth)")
    @SuppressWarnings({"unchecked"})
    public Object aroundGetItemsForSolarYearMonth(final ProceedingJoinPoint joinPoint, final YearMonth solarYearMonth)
            throws Throwable {
        return Optional.of(lunarCalendarDateRepository.findAllByMonthSolarOrderBySolarDateAsc(solarYearMonth))
                .filter(l -> !l.isEmpty())
                .map(l -> l.stream().map(LunarCalendarDate::toItem).collect(Collectors.toList()))
                .orElseGet(() -> {
                    final List<Response.Body.Item> items;
                    try {
                        items = (List<Response.Body.Item>) joinPoint.proceed();
                    } catch (final Throwable t) {
                        throw new RuntimeException(t);
                    }
                    final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        items.stream().map(LunarCalendarDate::from)
                                .peek(d -> d.setMonthSolar(solarYearMonth))
                                .forEach(lunarCalendarDateRepository::save);
                    });
                    return items;
                });
    }

    // -----------------------------------------------------------------------------------------------------------------
    @SuppressWarnings({"unchecked"})
    @Around("execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarCalendarService.getItemsForLunarDate(..))"
            + " && args(lunarYear,lunarMonth,lunarDayOfMonth)")
    public Object aroundGetByLunarDate(final ProceedingJoinPoint joinPoint, final Year lunarYear,
                                       final Month lunarMonth, final int lunarDayOfMonth)
            throws Throwable {
        return Optional.of(lunarCalendarDateRepository.findAllByLunarYearAndLunarMonthAndLunarDay(
                lunarYear.getValue(), lunarMonth, lunarDayOfMonth))
                .filter(l -> !l.isEmpty())
                .map(l -> l.stream().map(LunarCalendarDate::toItem).collect(Collectors.toList()))
                .orElseGet(() -> {
                    final List<Response.Body.Item> items;
                    try {
                        items = (List<Response.Body.Item>) joinPoint.proceed();
                    } catch (final Throwable t) {
                        throw new RuntimeException(t);
                    }
                    final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        items.stream()
                                .map(LunarCalendarDate::from)
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
                .map(l -> l.stream().map(LunarCalendarDate::toItem).collect(Collectors.toList()))
                .orElseGet(() -> {
                    final List<Response.Body.Item> items;
                    try {
                        items = (List<Response.Body.Item>) joinPoint.proceed();
                    } catch (final Throwable t) {
                        throw new RuntimeException(t);
                    }
                    final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        items.stream()
                                .map(LunarCalendarDate::from)
                                .peek(d -> d.setMonthLunar(lunarYearMonth))
                                .forEach(lunarCalendarDateRepository::save);
                    });
                    return items;
                });
    }

    private final LunarCalendarDateRepository lunarCalendarDateRepository;
}
