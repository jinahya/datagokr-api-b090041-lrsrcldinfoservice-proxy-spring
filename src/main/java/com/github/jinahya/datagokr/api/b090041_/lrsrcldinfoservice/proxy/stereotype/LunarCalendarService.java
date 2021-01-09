package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.LrsrCldInfoServiceClient;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response.Body.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Validated
@Service
@Slf4j
public class LunarCalendarService {

    // --------------------------------------------------------------------------------------------- getItemsForSolar...
    @Cacheable(cacheNames = {"itemsForSolarDate"})
    public @NotEmpty List<@Valid @NotNull Item> getItemsForSolarDate(@NotNull final LocalDate solarDate) {
        return lrsrCldInfoServiceClient.getLunCalInfo(solarDate);
    }

    @Cacheable(cacheNames = {"itemsForSolarYearMonth"})
    @Transactional
    public @NotEmpty List<@Valid @NotNull Item> getItemsForSolarYearMonth(@NotNull final YearMonth solarYearMonth) {
        return lrsrCldInfoServiceClient.getLunCalInfo(solarYearMonth);
    }

    @Cacheable(cacheNames = {"itemsForSolarYear"})
    @Transactional
    public @NotNull @NotEmpty List<@Valid @NotNull Item> getItemsForSolarYear(@NotNull final Year solarYear) {
        return Arrays
                .stream(Month.values())
                .map(m -> YearMonth.of(solarYear.getValue(), m))
                .<Supplier<List<Item>>>map(m -> () -> lunarCalendarService.getItemsForSolarYearMonth(m))
                .map(CompletableFuture::supplyAsync)
                .map(f -> {
                    try {
                        return f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    // ---------------------------------------------------------------------------------------------- getItemsByLunar...
    @Cacheable(cacheNames = {"itemsForLunarDate"})
    @Transactional
    public @NotNull List<@Valid @NotNull Item> getItemsForLunarDate(
            @NotNull final Year lunarYear, @NotNull final Month lunarMonth,
            @Max(30) @Min(1) final int lunarDayOfMonth) {
        return lrsrCldInfoServiceClient.getSolCalInfo(lunarYear, lunarMonth, lunarDayOfMonth);
    }

    @Cacheable(cacheNames = {"itemsForLunarYearMonth"})
    @Transactional
    public @NotEmpty List<@Valid @NotNull Item> getItemsForLunarYearMonth(@NotNull final YearMonth lunarYearMonth) {
        return lrsrCldInfoServiceClient.getSolCalInfo(lunarYearMonth);
    }

    @Cacheable(cacheNames = {"itemsForLunarYear"})
    @Transactional
    public @NotEmpty List<@Valid @NotNull Item> getItemsForLunarYear(@NotNull final Year lunarYear) {
        return Arrays
                .stream(Month.values())
                .map(m -> YearMonth.of(lunarYear.getValue(), m))
                .<Supplier<List<Item>>>map(m -> () -> lunarCalendarService.getItemsForLunarYearMonth(m))
                .map(CompletableFuture::supplyAsync)
                .map(f -> {
                    try {
                        return f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    private LrsrCldInfoServiceClient lrsrCldInfoServiceClient;

    // self injection for years
    @Lazy
    @Autowired
    private LunarCalendarService lunarCalendarService;
}
