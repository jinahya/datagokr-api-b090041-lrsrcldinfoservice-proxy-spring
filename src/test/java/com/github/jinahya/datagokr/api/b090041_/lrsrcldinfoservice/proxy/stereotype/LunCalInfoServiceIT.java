package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunCalInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Slf4j
class LunCalInfoServiceIT {

    @Test
    void testGetItemForSolarDate() {
        final LocalDate solarDate = LocalDate.now();
        final Optional<LunCalInfo> got = lrsrCldInfoService.getItemForSolarDate(solarDate);
    }

    @Autowired
    private LrsrCldInfoService lrsrCldInfoService;
}