package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.LrsrCldInfoServiceReactiveClient;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Month;
import java.time.Year;

import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_FROM_SOL_YEAR;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_LEAP_MONTH;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_LUN_DAY;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_LUN_MONTH;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_LUN_YEAR;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_SOL_DAY;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_SOL_MONTH;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_SOL_YEAR;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_TO_SOL_YEAR;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind.WebBindConstants.TAG_LUNAR;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind.WebBindConstants.TAG_SOLAR;

@RestController
@RequestMapping(path = {'/' + V1Controller.REQUEST_MAPPING_PATH})
@RequiredArgsConstructor
@Slf4j
public class V1Controller {

    public static final String REQUEST_MAPPING_PATH = "v1";

    @Operation(tags = {TAG_LUNAR}, summary = "Reads from /getSolCalInfo")
    @Cacheable(cacheNames = {"getLunCalInfo"})
    @GetMapping(path = {"/getLunCalInfo"},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_NDJSON_VALUE})
    public Flux<Response> getLunCalInfo(
            @RequestParam(QUERY_PARAM_NAME_SOL_YEAR) final int solYear,
            @RequestParam(QUERY_PARAM_NAME_SOL_MONTH) final int solMonth,
            @RequestParam(value = QUERY_PARAM_NAME_SOL_DAY, required = false) final Integer solDay) {
        return client.getLunCalInfoForAllPages(Year.of(solYear), Month.of(solMonth), solDay);
    }

    @Operation(tags = {TAG_SOLAR}, summary = "Reads from /getSolCalInfo")
    @Cacheable(cacheNames = {"getSolCalInfo"})
    @GetMapping(path = {"/getSolCalInfo"},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_NDJSON_VALUE})
    public Flux<Response> getSolCalInfo(
            @RequestParam(QUERY_PARAM_NAME_LUN_YEAR) final int lunYear,
            @RequestParam(QUERY_PARAM_NAME_LUN_MONTH) final int lunMonth,
            @RequestParam(value = QUERY_PARAM_NAME_LUN_DAY, required = false) final Integer lunDay) {
        return client.getSolCalInfoForAllPages(Year.of(lunYear), Month.of(lunMonth), lunDay);
    }

    @Operation(tags = {TAG_LUNAR}, summary = "Reads from /getSpcifyLunCalInfo")
    @Cacheable(cacheNames = {"getSpcifyLunCalInfo"})
    @GetMapping(path = {"/getSpcifyLunCalInfo"},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_NDJSON_VALUE})
    public Flux<Response> getSpcifyLunCalInfo(
            @RequestParam(QUERY_PARAM_NAME_FROM_SOL_YEAR) final int fromSolYear,
            @RequestParam(QUERY_PARAM_NAME_TO_SOL_YEAR) final int toSolYear,
            @RequestParam(QUERY_PARAM_NAME_LUN_MONTH) final int lunMonth,
            @RequestParam(QUERY_PARAM_NAME_LUN_DAY) final int lunDay,
            @RequestParam(QUERY_PARAM_NAME_LEAP_MONTH) final boolean leapMonth) {
        return client.getSpcifyLunCalInfoForAllPages(
                Year.of(fromSolYear), Year.of(toSolYear), Month.of(lunMonth), lunDay, leapMonth);
    }

    private final LrsrCldInfoServiceReactiveClient client;
}
