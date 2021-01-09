package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.ResponseResources;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Slf4j
class LunarCalendarDateRepositoryTest {

    public static Stream<LunarCalendarDate> entities() {
        return ResponseResources.items().map(LunarCalendarDate::from);
    }

    @MethodSource("entities")
    @ParameterizedTest
    void save_(final LunarCalendarDate entity) {
        final LunarCalendarDate saved = lunarCalendarDateRepository.save(entity);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void deleteAllBySolarDateIsLessThan() {
        final List<Response.Body.Item> items = ResponseResources.items().collect(Collectors.toList());
        items.stream()
                .map(LunarCalendarDate::from)
                .peek(v -> v.setSolarDate(v.getSolarDate().minusDays(365L)))
                .forEach(lunarCalendarDateRepository::save);
        final int count = lunarCalendarDateRepository.deleteAllBySolarDateIsLessThan(LocalDate.now());
        assertThat(count).isEqualTo(items.stream().map(Response.Body.Item::getSolarDate).count());
    }

    @Test
    void deleteAllBySolarDateIsLessThanLimitNative() {
        final TemporalAmount period = Period.ofDays(10);
        final List<Response.Body.Item> items = ResponseResources.items().collect(Collectors.toList());
        items.stream()
                .map(LunarCalendarDate::from)
                .peek(v -> v.setSolarDate(v.getSolarDate().minusDays(365L)))
                .forEach(lunarCalendarDateRepository::save);
        final int count = lunarCalendarDateRepository.deleteAllBySolarDateIsLessThanLimitNative(LocalDate.now());
        assertThat(count).isEqualTo(items.stream().map(Response.Body.Item::getSolarDate).count());
    }

    @Autowired
    private LunarCalendarDateRepository lunarCalendarDateRepository;
}