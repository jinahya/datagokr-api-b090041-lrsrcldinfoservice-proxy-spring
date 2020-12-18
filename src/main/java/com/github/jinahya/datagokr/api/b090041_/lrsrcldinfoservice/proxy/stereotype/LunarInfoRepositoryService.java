package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunarInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@Validated
@Service
@RequiredArgsConstructor
@Slf4j
public class LunarInfoRepositoryService {

    /**
     * Finds a lunar calendar date associated to specified solar calendar date.
     *
     * @param solarDate the solar calendar date.
     * @return an optional of lunar calendar date; May be {@link Optional#empty() empty} when any suppressed error
     * occurs.
     */
    public @NotNull Optional<LunarCalendarDate> findBySolarDate(final @NotNull LocalDate solarDate) {
        return lunarInfoRepository.findBySolarDate(solarDate);
    }

    public @NotNull Optional<LunarCalendarDate> findByLunarDate(final @NotNull LocalDate lunarDate) {
        return lunarInfoRepository.findByLunarDate(lunarDate);
    }

    public @NotNull LunarCalendarDate save(final @Valid @NotNull LunarCalendarDate lunarCalendarDate) {
        try {
            return lunarInfoRepository.save(lunarCalendarDate);
        } catch (final Exception e) {
            if (e instanceof DuplicateKeyException) {
                final LocalDate solarDate = lunarCalendarDate.getSolarDate();
                final LunarCalendarDate foundBySolarDate = lunarInfoRepository.findBySolarDate(solarDate).orElse(null);
                final LocalDate lunarDate = lunarCalendarDate.getLunarDate();
                final LunarCalendarDate foundByLunarDate = lunarInfoRepository.findByLunarDate(lunarDate).orElse(null);
                Assert.isTrue(foundBySolarDate != null || foundByLunarDate != null,
                              () -> "duplicate key exception thrown yet not found by either " + solarDate
                                    + " nor " + lunarDate);
            } else {
                log.error("failed to save {}", lunarCalendarDate, e);
            }
        }
        return lunarCalendarDate;
    }

    private final LunarInfoRepository lunarInfoRepository;
}
