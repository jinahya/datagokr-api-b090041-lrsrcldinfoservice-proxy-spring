package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
@Slf4j
public class LunarCalendarDateRepositoryAspect {

    /**
     * Handles {@link DataIntegrityViolationException} {@link Around around} the {@link
     * LunarCalendarDateRepository#save(Object)} method.
     *
     * @param joinPoint a proceeding join point.
     * @param entity    the {@code entity} argument.
     * @return the proceeded result.
     * @throws Throwable if any error thrown.
     */
    @Around("execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunarCalendarDateRepository.save(..))"
            + " && args(entity)")
    public Object aroundSave(final ProceedingJoinPoint joinPoint, final LunarCalendarDate entity) throws Throwable {
        ((LunarCalendarDateRepository) joinPoint.getTarget()).findById(entity.getSolarDate())
                .ifPresent(existing -> {
                    Optional.ofNullable(existing.getLunarMonthAggregated()).ifPresent(entity::setLunarMonthAggregated);
                    Optional.ofNullable(existing.getSolarMonthAggregated()).ifPresent(entity::setSolarMonthAggregated);
                });
        try {
            return joinPoint.proceed();
        } catch (final DataIntegrityViolationException dive) {
            return ((LunarCalendarDateRepository) joinPoint.getTarget())
                    .findById(entity.getSolarDate())
                    .orElseThrow(() -> new RuntimeException(
                            "data integrity violation exception thrown yet not found: " + entity));
        }
    }
}
