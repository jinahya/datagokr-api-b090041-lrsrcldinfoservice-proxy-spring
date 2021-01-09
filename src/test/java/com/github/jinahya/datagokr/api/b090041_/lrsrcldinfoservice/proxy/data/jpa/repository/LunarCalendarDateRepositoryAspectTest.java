package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.YearMonth;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class LunarCalendarDateRepositoryAspectTest {

    @MethodSource("com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.ResponseResources#items")
    @ParameterizedTest
    void save_(final Response.Body.Item item) {
        final LunarCalendarDate saved1 = lunarCalendarDateRepository.save(LunarCalendarDate.from(item));
        final YearMonth monthLunar = current().nextBoolean() ? null : YearMonth.now();
        saved1.setMonthLunar(monthLunar);
        final YearMonth monthSolar = current().nextBoolean() ? null : YearMonth.now();
        saved1.setMonthSolar(monthSolar);
        final LunarCalendarDate saved2 = lunarCalendarDateRepository.save(saved1);
        final LunarCalendarDate saved3 = lunarCalendarDateRepository.save(LunarCalendarDate.from(item));
        assertThat(saved3.getMonthLunar()).isEqualTo(monthLunar);
        assertThat(saved3.getMonthSolar()).isEqualTo(monthSolar);
    }

    @Autowired
    private LunarCalendarDateRepository lunarCalendarDateRepository;
}
