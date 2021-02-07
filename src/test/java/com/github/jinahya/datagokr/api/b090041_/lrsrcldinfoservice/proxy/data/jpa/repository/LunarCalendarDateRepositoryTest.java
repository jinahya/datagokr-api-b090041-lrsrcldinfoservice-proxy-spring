package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Item;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.ResponseResources;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDateMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

@DataJpaTest
@Slf4j
class LunarCalendarDateRepositoryTest {

    public static Stream<LunarCalendarDate> entities() {
        return ResponseResources.items()
                .map(getMapper(LunarCalendarDateMapper.class)::fromItem);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @PostConstruct
    private void onPostConstruct() {
        lunarCalendarDateMapper = Mappers.getMapper(LunarCalendarDateMapper.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @MethodSource("entities")
    @ParameterizedTest
    void save_(final LunarCalendarDate entity) {
        final LunarCalendarDate saved = lunarCalendarDateRepository.save(entity);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void deleteAllBySolarDateIsLessThan() {
        final List<Item> items = ResponseResources.items().collect(toList());
        items.stream()
                .map(lunarCalendarDateMapper::fromItem)
                .peek(v -> v.setSolarDate(v.getSolarDate().minusDays(365L)))
                .forEach(lunarCalendarDateRepository::save);
        final int count = lunarCalendarDateRepository.deleteAllBySolarDateIsLessThan(LocalDate.now());
        assertThat(count).isEqualTo(items.stream().map(Item::getSolarDate).count());
    }

    @Test
    void deleteAllBySolarDateIsLessThanLimitNative() {
        final TemporalAmount period = Period.ofDays(10);
        final List<Item> items = ResponseResources.items().collect(toList());
        items.stream()
                .map(lunarCalendarDateMapper::fromItem)
                .peek(v -> v.setSolarDate(v.getSolarDate().minusDays(365L)))
                .forEach(lunarCalendarDateRepository::save);
        final int count = lunarCalendarDateRepository.deleteAllBySolarDateIsLessThanLimitNative(LocalDate.now());
        assertThat(count).isEqualTo(items.stream().map(Item::getSolarDate).count());
    }

    @Autowired
    private LunarCalendarDateRepository lunarCalendarDateRepository;

    private LunarCalendarDateMapper lunarCalendarDateMapper;
}