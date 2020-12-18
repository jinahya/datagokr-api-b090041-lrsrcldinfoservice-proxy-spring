package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LunarInfoRepository
        extends JpaRepository<LunarCalendarDate, LocalDate>, JpaSpecificationExecutor<LunarCalendarDate> {

    Optional<LunarCalendarDate> findBySolarDate(LocalDate solarDate);

    Optional<LunarCalendarDate> findByLunarDate(LocalDate lunarDate);
}
