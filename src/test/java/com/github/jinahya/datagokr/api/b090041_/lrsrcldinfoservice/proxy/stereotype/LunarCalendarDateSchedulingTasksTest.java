package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunarCalendarDateRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class LunarCalendarDateSchedulingTasksTest {

    @Test
    void a() {
//        ResponseResources.items()
//                .map(LunarCalendarDate::from)
//                .peek(v -> v.setSolarDate(v.getSolarDate().minus(PERIOD_FOR_PURGING_FUTURE_ENTRIES_BY_SOLAR_DATE).minus(Duration.ofSeconds(1L))))
//                .forEach(lunarCalendarDateRepository::save);
//        final int count = lunarCalendarDateSchedulingTasks.purgePastEntriesBySolarDate();
    }

    @Autowired
    private LunarCalendarDateRepository lunarCalendarDateRepository;

    @Autowired
    private LunarCalendarDateSchedulingTasks lunarCalendarDateSchedulingTasks;
}