package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarCalendarService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import javax.validation.constraints.Min;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response.Body.Item.MAX_DAY_OF_MONTH_LUNAR;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response.Body.Item.MAX_DAY_OF_MONTH_SOLAR;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response.Body.Item.MIN_DAY_OF_MONTH_LUNAR;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response.Body.Item.MIN_DAY_OF_MONTH_SOLAR;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = {LunarCalendarController.REQUEST_MAPPING_PATH})
@RequiredArgsConstructor
@Slf4j
public class LunarCalendarController {

    public static final String REQUEST_MAPPING_PATH = "/v2";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PATH_NAME_YEAR = "year";

    public static final String PATH_VALUE_YEAR = "\\d{4}";

    public static final String PATH_TEMPLATE_YEAR = '{' + PATH_NAME_YEAR + ':' + PATH_VALUE_YEAR + '}';

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PATH_NAME_MONTH = "month";

    public static final String PATH_VALUE_MONTH = "^(?:0?[1-9]|1[012])$";

    public static final String PATH_TEMPLATE_MONTH = '{' + PATH_NAME_MONTH + ':' + PATH_VALUE_MONTH + '}';

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PATH_NAME_DAY_OF_MONTH = "dayOfMonth";

    public static final String PATH_VALUE_DAY_OF_MONTH = "^(?:0[1-9]|[12]\\d|3[01])$";

    public static final String PATH_TEMPLATE_DAY_OF_MONTH
            = '{' + PATH_NAME_DAY_OF_MONTH + ':' + PATH_VALUE_DAY_OF_MONTH + '}';

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PATH_VALUE_LUNAR = "lunar";

    public static final String PATH_VALUE_SOLAR = "solar";

    private static Response.Body.Item links(final Response.Body.Item item) {
        item.add(linkTo(LunarCalendarController.class)
                         .slash(PATH_VALUE_LUNAR)
                         .slash(item.getLunarYear().getValue())
                         .withRel(Response.Body.Item.REL_YEAR_LUNAR));
        item.add(linkTo(LunarCalendarController.class)
                         .slash(PATH_VALUE_LUNAR)
                         .slash(item.getLunarYear().getValue())
                         .slash(item.getLunarMonth().getValue())
                         .withRel(Response.Body.Item.REL_MONTH_LUNAR));
        item.add(linkTo(LunarCalendarController.class)
                         .slash(PATH_VALUE_LUNAR)
                         .slash(item.getLunarYear().getValue())
                         .slash(item.getLunarMonth().getValue())
                         .slash(item.getLunarDayOfMonth())
                         .withRel(Response.Body.Item.REL_DATE_LUNAR));
        item.add(linkTo(LunarCalendarController.class)
                         .slash(PATH_VALUE_SOLAR)
                         .slash(item.getSolarYear().getValue())
                         .withRel(Response.Body.Item.REL_YEAR_SOLAR));
        item.add(linkTo(LunarCalendarController.class)
                         .slash(PATH_VALUE_SOLAR)
                         .slash(item.getSolarYear().getValue())
                         .slash(item.getSolarMonth().getValue())
                         .withRel(Response.Body.Item.REL_MONTH_SOLAR));
        item.add(linkTo(LunarCalendarController.class)
                         .slash(PATH_VALUE_SOLAR)
                         .slash(item.getSolarYear().getValue())
                         .slash(item.getSolarMonth().getValue())
                         .slash(item.getSolarDayOfMonth())
                         .withRel(Response.Body.Item.REL_DATE_SOLAR));
        return item;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TAG_LUNAR = "lunar";

    public static final String TAG_SOLAR = "solar";

    // -----------------------------------------------------------------------------------------------------------------
    private static Year yearOf(final int isoYear) {
        try {
            return Year.of(isoYear);
        } catch (final DateTimeException dte) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                              "invalid isoYear(" + isoYear + "); message: " + dte.getMessage());
        }
    }

    private static Month monthOf(final int month) {
        try {
            return Month.of(month);
        } catch (final DateTimeException dte) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                              "invalid month(" + month + "); message: " + dte.getMessage());
        }
    }

    // ----------------------------------------------------------------------------------------------------- /lunar/uuuu
    @Operation(tags = {TAG_LUNAR}, summary = "Reads all dates in specified year")
    @GetMapping(
            path = {
                    '/' + PATH_VALUE_LUNAR + '/' + PATH_TEMPLATE_YEAR
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    public Flux<Response.Body.Item> readLunarYear(@PathVariable(PATH_NAME_YEAR) final int isoYear) {
        final Year year = yearOf(isoYear);
        return Flux.fromIterable(lunarCalendarService.getItemsForLunarYear(year))
//                .sort(Response.Body.Item.COMPARING_IN_LUNAR)
                .map(LunarCalendarController::links)
                ;
    }

    // --------------------------------------------------------------------------------------------------- /lunar/uuuu/M
    @Operation(tags = {TAG_LUNAR}, summary = "Reads all dates in specified month in a year")
    @GetMapping(
            path = {
                    '/' + PATH_VALUE_LUNAR + '/' + PATH_TEMPLATE_YEAR + '/' + PATH_TEMPLATE_MONTH
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    public Flux<Response.Body.Item> readLunarYearMonth(@PathVariable(PATH_NAME_YEAR) final int year,
                                                       @PathVariable(name = PATH_NAME_MONTH) final int month) {
        final YearMonth yearMonth = YearMonth.of(yearOf(year).getValue(), monthOf(month));
        return Flux.fromIterable(lunarCalendarService.getItemsForLunarYearMonth(yearMonth))
//                .sort(Response.Body.Item.COMPARING_IN_LUNAR)
                .map(LunarCalendarController::links)
                ;
    }

    // ------------------------------------------------------------------------------------------------- /lunar/uuuu/M/d
    @Operation(tags = {TAG_LUNAR}, summary = "Reads a date of specified year, month, and day-of-month")
    @GetMapping(
            path = {
                    '/' + PATH_VALUE_LUNAR + '/' + PATH_TEMPLATE_YEAR + '/' + PATH_TEMPLATE_MONTH
                    + '/' + PATH_TEMPLATE_DAY_OF_MONTH
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    public Flux<Response.Body.Item> readLunarYearMonthDay(
            @PathVariable(PATH_NAME_YEAR) final int year,
            @PathVariable(name = PATH_NAME_MONTH) final int month,
            @Min(MAX_DAY_OF_MONTH_LUNAR) @Min(MIN_DAY_OF_MONTH_LUNAR) @PathVariable(name = PATH_NAME_DAY_OF_MONTH)
            final int dayOfMonth) {
        return Flux.fromIterable(lunarCalendarService.getItemsForLunarDate(yearOf(year), monthOf(month), dayOfMonth))
                .map(LunarCalendarController::links)
                ;
    }

    // ----------------------------------------------------------------------------------------------------- /solar/uuuu
    @Operation(tags = {TAG_SOLAR}, summary = "Reads all dates in specified year")
    @GetMapping(
            path = {
                    '/' + PATH_VALUE_SOLAR + '/' + PATH_TEMPLATE_YEAR
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    public Flux<Response.Body.Item> readSolarYear(@PathVariable(PATH_NAME_YEAR) final int isoYear) {
        final Year year = yearOf(isoYear);
        return Flux.fromIterable(lunarCalendarService.getItemsForSolarYear(year))
//                .sort(Response.Body.Item.COMPARING_IN_SOLAR)
                .map(LunarCalendarController::links)
                ;
    }

    // --------------------------------------------------------------------------------------------------- /solar/uuuu/M
    @Operation(tags = {TAG_SOLAR}, summary = "Reads all dates in specified month in a year")
    @GetMapping(
            path = {
                    '/' + PATH_VALUE_SOLAR + '/' + PATH_TEMPLATE_YEAR + '/' + PATH_TEMPLATE_MONTH
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    public Flux<Response.Body.Item> readSolarYearMonth(@PathVariable(PATH_NAME_YEAR) final int year,
                                                       @PathVariable(name = PATH_NAME_MONTH) final int month) {
        final YearMonth yearMonth = YearMonth.of(yearOf(year).getValue(), monthOf(month));
        return Flux.fromIterable(lunarCalendarService.getItemsForSolarYearMonth(yearMonth))
//                .sort(Response.Body.Item.COMPARING_IN_SOLAR)
                .map(LunarCalendarController::links)
                ;
    }

    // ------------------------------------------------------------------------------------------------- /solar/uuuu/M/d
    @Operation(tags = {TAG_SOLAR}, summary = "Reads a date of specified year, month, and day-of-month")
    @GetMapping(
            path = {
                    '/' + PATH_VALUE_SOLAR + '/' + PATH_TEMPLATE_YEAR + '/' + PATH_TEMPLATE_MONTH
                    + '/' + PATH_TEMPLATE_DAY_OF_MONTH
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    public Flux<Response.Body.Item> readSolarYearMonthDayOfMonth(
            @PathVariable(PATH_NAME_YEAR) final int year,
            @PathVariable(name = PATH_NAME_MONTH) final int month,
            @Min(MAX_DAY_OF_MONTH_SOLAR) @Min(MIN_DAY_OF_MONTH_SOLAR) @PathVariable(name = PATH_NAME_DAY_OF_MONTH)
            final int dayOfMonth) {
        final LocalDate localDate;
        try {
            localDate = LocalDate.of(yearOf(year).getValue(), monthOf(month), dayOfMonth);
        } catch (final DateTimeException dte) {
            log.error("failed to create LocalDate with {}, {}, {}", year, month, dayOfMonth, dte);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                              "invalid dayOfMonth(" + dayOfMonth + ") for year(" + year + ") and month("
                                              + month + "); message: " + dte.getMessage());
        }
        return Flux.fromIterable(lunarCalendarService.getItemsForSolarDate(localDate))
                .map(LunarCalendarController::links)
                ;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private final LunarCalendarService lunarCalendarService;
}
