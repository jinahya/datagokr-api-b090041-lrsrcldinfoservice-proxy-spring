package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunarInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@Validated
@Service
@RequiredArgsConstructor
@Slf4j
public class LunarInfoService {

    //@Cacheable(cacheNames = "itemBySolarDate")
    public Optional<LunarCalendarDate> getBySolarDate(@NotNull final LocalDate solarDate) {
        return lunarInfoRepository.findBySolarDate(solarDate);
    }

    //@Cacheable(cacheNames = "itemByLunarDate")
    public Optional<LunarCalendarDate> getByLunarDate(@NotNull final LocalDate lunarDate) {
        return lunarInfoRepository.findByLunarDate(lunarDate);
    }

    private final LunarInfoRepository lunarInfoRepository;
}
