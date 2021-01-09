package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarCalendarService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = {LunarCalendarV2Controller.REQUEST_MAPPING_PATH})
@RequiredArgsConstructor
@Slf4j
public class LunarCalendarV2Controller {

    public static final String REQUEST_MAPPING_PATH = "/v2";

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

    private static Response.Body.Item links(final Response.Body.Item item) {
        item.add(linkTo(LunarCalendarV2Controller.class)
                         .slash(PATH_VALUE_LUNAR)
                         .slash(item.getLunarYear().getValue())
                         .withRel(Response.Body.Item.REL_YEAR_LUNAR));
        item.add(linkTo(LunarCalendarV2Controller.class)
                         .slash(PATH_VALUE_LUNAR)
                         .slash(item.getLunarYear().getValue())
                         .slash(item.getLunarMonth().getValue())
                         .withRel(Response.Body.Item.REL_MONTH_LUNAR));
        item.add(linkTo(LunarCalendarV2Controller.class)
                         .slash(PATH_VALUE_LUNAR)
                         .slash(item.getLunarYear().getValue())
                         .slash(item.getLunarMonth().getValue())
                         .slash(item.getLunarDayOfMonth())
                         .withRel(Response.Body.Item.REL_SELF_LUNAR));
        item.add(linkTo(LunarCalendarV2Controller.class)
                         .slash(PATH_VALUE_SOLAR)
                         .slash(item.getSolarYear().getValue())
                         .withRel(Response.Body.Item.REL_YEAR_SOLAR));
        item.add(linkTo(LunarCalendarV2Controller.class)
                         .slash(PATH_VALUE_SOLAR)
                         .slash(item.getSolarYear().getValue())
                         .slash(item.getSolarMonth().getValue())
                         .withRel(Response.Body.Item.REL_MONTH_SOLAR));
        item.add(linkTo(LunarCalendarV2Controller.class)
                         .slash(PATH_VALUE_SOLAR)
                         .slash(item.getSolarYear().getValue())
                         .slash(item.getSolarMonth().getValue())
                         .slash(item.getSolarDayOfMonth())
                         .withRel(Response.Body.Item.REL_SELF_SOLAR));
        return item;
    }

    // ------------------------------------------------------------------------------------------------- /lunar/uuuu/M/d
    @GetMapping(
            path = {
                    '/' + PATH_VALUE_LUNAR + '/' + PATH_TEMPLATE_YEAR,
                    '/' + PATH_VALUE_LUNAR + '/' + PATH_TEMPLATE_YEAR + '/' + PATH_TEMPLATE_MONTH,
                    '/' + PATH_VALUE_LUNAR + '/' + PATH_TEMPLATE_YEAR + '/' + PATH_TEMPLATE_MONTH
                    + '/' + PATH_TEMPLATE_DAY_OF_MONTH
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    public Flux<Response.Body.Item> readLunar(
            @NotNull @PathVariable(PATH_NAME_YEAR) @DateTimeFormat(pattern = "uuuu") final int year,
            @PathVariable(name = PATH_NAME_MONTH, required = false) @DateTimeFormat(pattern = "M") final Integer month,
            @PathVariable(name = PATH_NAME_DAY_OF_MONTH, required = false) @DateTimeFormat(pattern = "d")
            final Integer dayOfMonth) {
        if (dayOfMonth != null) {
            Assert.notNull(month, () -> "month should be not null");
            return Flux.fromIterable(lunarCalendarService.getItemsForLunarDate(
                    Year.of(year), Month.of(month), dayOfMonth))
                    .map(LunarCalendarV2Controller::links)
                    ;
        }
        if (month != null) {
            return Flux.fromIterable(
                    lunarCalendarService.getItemsForLunarYearMonth(YearMonth.of(year, month)))
                    .map(LunarCalendarV2Controller::links)
                    ;
        }
        return Flux.fromIterable(lunarCalendarService.getItemsForLunarYear(Year.of(year)))
                .map(LunarCalendarV2Controller::links)
                ;
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
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    public Flux<Response.Body.Item> readSolar(
            @NotNull @PathVariable(PATH_NAME_YEAR) @DateTimeFormat(pattern = "uuuu") final int year,
            @PathVariable(name = PATH_NAME_MONTH, required = false) @DateTimeFormat(pattern = "M") final Integer month,
            @PathVariable(name = PATH_NAME_DAY_OF_MONTH, required = false) @DateTimeFormat(pattern = "d")
            final Integer dayOfMonth) {
        if (dayOfMonth != null) {
            Assert.notNull(month, () -> "month should be not null");
            return Flux.fromIterable(
                    lunarCalendarService.getItemsForSolarDate(LocalDate.of(year, month, dayOfMonth)))
                    .map(LunarCalendarV2Controller::links)
                    ;
        }
        if (month != null) {
            return Flux.fromIterable(
                    lunarCalendarService.getItemsForSolarYearMonth(YearMonth.of(year, month)))
                    .map(LunarCalendarV2Controller::links)
                    ;
        }
        return Flux.fromIterable(lunarCalendarService.getItemsForSolarYear(Year.of(year)))
                .map(LunarCalendarV2Controller::links)
                ;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private final LunarCalendarService lunarCalendarService;
}
