package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class RemoteLunCalInfoServiceIT {

    @Test
    void testGetRunCalInfo() throws Throwable {
        final LocalDate solarDate = LocalDate.now();
        final LunCalInfoItem item = remoteLrsrCldInfoService.getLunCalInfo(solarDate).block();
        assertThat(item).isNotNull();
    }

    @Autowired
    private RemoteLrsrCldInfoService remoteLrsrCldInfoService;
}