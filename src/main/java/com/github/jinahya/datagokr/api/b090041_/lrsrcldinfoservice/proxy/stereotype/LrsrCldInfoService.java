package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunCalInfo;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunCalInfoRepository;
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
public class LrsrCldInfoService {

    //@Cacheable(cacheNames = "itemBySolarDate")
    public Optional<LunCalInfo> getItemForSolarDate(@NotNull final LocalDate solarDate) {
        return lunCalInfoRepository.findBySolarDate(solarDate);
    }

    //@Cacheable(cacheNames = "itemByLunarDate")
    public Optional<LunCalInfo> getItemForLunarDate(@NotNull final LocalDate lunarDate) {
        return lunCalInfoRepository.findByLunarDate(lunarDate);
    }

    private final LunCalInfoRepository lunCalInfoRepository;
}
