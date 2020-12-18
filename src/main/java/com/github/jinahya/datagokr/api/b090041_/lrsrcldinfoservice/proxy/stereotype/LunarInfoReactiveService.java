package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunarInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Validated
@Service
@RequiredArgsConstructor
@Slf4j
public class LunarInfoReactiveService {

    //@Cacheable(cacheNames = "itemBySolarDate")
    public Mono<LunarCalendarDate> getBySolarDate(@NotNull final LocalDate solarDate) {
        return Mono.justOrEmpty(lunarInfoRepository.findBySolarDate(solarDate));
    }

    //@Cacheable(cacheNames = "itemByLunarDate")
    public Mono<LunarCalendarDate> getByLunarDate(@NotNull final LocalDate lunarDate) {
        return Mono.justOrEmpty(lunarInfoRepository.findByLunarDate(lunarDate));
    }

    private final LunarInfoRepository lunarInfoRepository;
}
