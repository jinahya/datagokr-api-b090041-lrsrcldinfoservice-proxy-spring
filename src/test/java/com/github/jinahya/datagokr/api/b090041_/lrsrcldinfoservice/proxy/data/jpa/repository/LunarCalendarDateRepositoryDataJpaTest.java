package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
class LunarCalendarDateRepositoryDataJpaTest {

    @Test
    void testFindBySolarDate() {
        final Optional<LunarCalendarDate> found = lunarInfoRepository.findBySolarDate(LocalDate.now());
        assertThat(found).isNotNull().isEmpty();
    }

    @Test
    void testFindByLunarDate() {
        final Optional<LunarCalendarDate> found = lunarInfoRepository.findByLunarDate(LocalDate.now());
        assertThat(found).isNotNull().isEmpty();
    }

    @Autowired
    private LunarInfoRepository lunarInfoRepository;
}