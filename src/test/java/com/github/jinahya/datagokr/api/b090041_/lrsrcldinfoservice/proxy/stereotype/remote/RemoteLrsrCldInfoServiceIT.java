package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Sinks;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class RemoteLrsrCldInfoServiceIT {

    @Test
    void testGetLunCalInfo() {
        final LocalDate solarDate = LocalDate.now();
        final LunCalInfoItem item = remoteLrsrCldInfoService.getLunCalInfo(solarDate).block();
        assertThat(item).isNotNull();
    }

    @Test
    void test_getSolCalInfo_with_dayOfMonth() {
        final LocalDate lunarDate = LocalDate.now();
        final Year year = Year.of(lunarDate.getYear());
        final Month month = lunarDate.getMonth();
        final int dayOfMonth = lunarDate.getDayOfMonth();
        final Sinks.Many<Response.Body.Item> itemsSink = Sinks.many().unicast().onBackpressureBuffer();
        remoteLrsrCldInfoService.getSolCalInfo(year, month, dayOfMonth, itemsSink);
        itemsSink.asFlux()
                .doOnNext(item -> {
                    log.debug("item: {}", item);
                })
                .blockLast();
    }

    @Test
    void test_getSolCalInfo_without_dayOfMonth() {
        final LocalDate lunarDate = LocalDate.now();
        final Year year = Year.of(lunarDate.getYear());
        final Month month = lunarDate.getMonth();
        final Integer dayOfMonth = null;
        final Sinks.Many<Response.Body.Item> itemsSink = Sinks.many().unicast().onBackpressureBuffer();
        remoteLrsrCldInfoService.getSolCalInfo(year, month, dayOfMonth, itemsSink);
        itemsSink.asFlux()
                .doOnNext(item -> {
                    log.debug("item: {}", item);
                })
                .blockLast();
    }

    @Autowired
    private RemoteLrsrCldInfoService remoteLrsrCldInfoService;
}