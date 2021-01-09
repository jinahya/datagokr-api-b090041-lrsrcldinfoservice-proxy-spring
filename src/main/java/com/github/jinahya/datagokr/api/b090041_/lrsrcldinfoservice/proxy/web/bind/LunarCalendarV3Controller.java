package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarCalendarService;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind.type.LunarCalendarDateTypeV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

@RestController
@RequestMapping(path = {LunarCalendarV3Controller.REQUEST_MAPPING_PATH})
@RequiredArgsConstructor
@Slf4j
public class LunarCalendarV3Controller {

    public static final String REQUEST_MAPPING_PATH = "/v3";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PATH_NAME_YEAR = "year";

    public static final String PATH_VALUE_YEAR = "\\d{4}";

    public static final String PATH_TEMPLATE_YEAR = '{' + PATH_NAME_YEAR + ':' + PATH_VALUE_YEAR + '}';

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PATH_NAME_MONTH = "month";

    public static final String PATH_VALUE_MONTH = "\\d{1,2}";

    public static final String PATH_TEMPLATE_MONTH = '{' + PATH_NAME_MONTH + ':' + PATH_VALUE_MONTH + '}';

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PATH_NAME_DAY_OF_MONTH = "dayOfMonth";

    public static final String PATH_VALUE_DAY_OF_MONTH = "\\d{1,2}";

    public static final String PATH_TEMPLATE_DAY_OF_MONTH
            = '{' + PATH_NAME_DAY_OF_MONTH + ':' + PATH_VALUE_DAY_OF_MONTH + '}';

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PATH_VALUE_LUNAR = "lunar";

    public static final String PATH_VALUE_SOLAR = "solar";

    // ------------------------------------------------------------------------------------------------- /lunar/uuuu/M/d
    @GetMapping(
            path = {
                    '/' + PATH_VALUE_LUNAR + '/' + PATH_TEMPLATE_YEAR,
                    '/' + PATH_VALUE_LUNAR + '/' + PATH_TEMPLATE_YEAR + '/' + PATH_TEMPLATE_MONTH,
                    '/' + PATH_VALUE_LUNAR + '/' + PATH_TEMPLATE_YEAR + '/' + PATH_TEMPLATE_MONTH
                    + '/' + PATH_TEMPLATE_DAY_OF_MONTH
            },
            produces = {
//                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    public Flux<LunarCalendarDateTypeV3> readLunar(
            @NotNull @PathVariable(PATH_NAME_YEAR) @DateTimeFormat(pattern = "uuuu") final int year,
            @PathVariable(name = PATH_NAME_MONTH, required = false) @DateTimeFormat(pattern = "M") final Integer month,
            @PathVariable(name = PATH_NAME_DAY_OF_MONTH, required = false) @DateTimeFormat(pattern = "d")
            final Integer dayOfMonth) {
        if (dayOfMonth != null) {
            Assert.notNull(month, () -> "month should be not null");
            return Flux.fromIterable(lunarCalendarService.getItemsForLunarDate(
                    Year.of(year), Month.of(month), dayOfMonth))
                    .map(LunarCalendarDateTypeV3::from);
        }
        if (month != null) {
            return Flux.fromIterable(
                    lunarCalendarService.getItemsForLunarYearMonth(YearMonth.of(year, month)))
                    .map(LunarCalendarDateTypeV3::from);
        }
        return Flux.fromIterable(lunarCalendarService.getItemsForLunarYear(Year.of(year)))
                .map(LunarCalendarDateTypeV3::from);
    }

    // ------------------------------------------------------------------------------------------------- /solar/uuuu/M/d
    @GetMapping(
            path = {
                    '/' + PATH_VALUE_SOLAR + '/' + PATH_TEMPLATE_YEAR,
                    '/' + PATH_VALUE_SOLAR + '/' + PATH_TEMPLATE_YEAR + '/' + PATH_TEMPLATE_MONTH,
                    '/' + PATH_VALUE_SOLAR + '/' + PATH_TEMPLATE_YEAR + '/' + PATH_TEMPLATE_MONTH
                    + '/' + PATH_TEMPLATE_DAY_OF_MONTH
            },
            produces = {
//                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    public Flux<LunarCalendarDateTypeV3> readSolar(
            @NotNull @PathVariable(PATH_NAME_YEAR) @DateTimeFormat(pattern = "uuuu") final int year,
            @PathVariable(name = PATH_NAME_MONTH, required = false) @DateTimeFormat(pattern = "M") final Integer month,
            @PathVariable(name = PATH_NAME_DAY_OF_MONTH, required = false) @DateTimeFormat(pattern = "d")
            final Integer dayOfMonth) {
        if (dayOfMonth != null) {
            Assert.notNull(month, () -> "month should be not null");
            return Flux.fromIterable(
                    lunarCalendarService.getItemsForSolarDate(LocalDate.of(year, month, dayOfMonth)))
                    .map(LunarCalendarDateTypeV3::from);
        }
        if (month != null) {
            return Flux.fromIterable(
                    lunarCalendarService.getItemsForSolarYearMonth(YearMonth.of(year, month)))
                    .map(LunarCalendarDateTypeV3::from);
        }
        return Flux.fromIterable(lunarCalendarService.getItemsForSolarYear(Year.of(year)))
                .map(LunarCalendarDateTypeV3::from);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private final LunarCalendarService lunarCalendarService;
}
