package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunCalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LunCalInfoRepository
        extends JpaRepository<LunCalInfo, LocalDate>, JpaSpecificationExecutor<LunCalInfo> {

    Optional<LunCalInfo> findBySolarDate(LocalDate solarDate);

    Optional<LunCalInfo> findByLunarDate(LocalDate lunarDate);
}
