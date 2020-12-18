package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.LrsrCldInfoServiceClient;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LunarInfoServiceAspect {

    @Around(value = "execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarInfoService.getBySolarDate(..))"
                    + " && args(solarDate)")
    public Object aroundGetBySolarDate(final ProceedingJoinPoint joinPoint, final LocalDate solarDate)
            throws Throwable {
        final Object result = joinPoint.proceed();
        @SuppressWarnings({"unchecked"})
        final Optional<LunarCalendarDate> casted = (Optional<LunarCalendarDate>) result;
        if (casted.isEmpty()) {
            final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                final Response.Body.Item item;
                try {
                    item = lrsrCldInfoServiceClient.getLunCalInfo(solarDate);
                } catch (final Exception e) {
                    log.error("failed to getLunCalInfo({})", solarDate, e);
                    return;
                }
                final LunarCalendarDate saved = lunarInfoRepositoryService.save(LunarCalendarDate.from(item));
                log.debug("saved: {}", saved);
            });
        }
        return casted;
    }

    @Around(value = "execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarInfoService.getByLunarDate(..))"
                    + " && args(lunarDate)")
    public Object aroundGetByLunarDate(final ProceedingJoinPoint joinPoint, final LocalDate lunarDate)
            throws Throwable {
        final Object result = joinPoint.proceed();
        @SuppressWarnings({"unchecked"})
        final Optional<LunarCalendarDate> casted = (Optional<LunarCalendarDate>) result;
        if (casted.isEmpty()) {
            final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                final Response.Body.Item item;
                try {
                    item = lrsrCldInfoServiceClient.getSolCalInfo(lunarDate);
                } catch (final Exception e) {
                    log.error("failed to getSolCalInfo({})", lunarDate, e);
                    return;
                }
                final LunarCalendarDate saved = lunarInfoRepositoryService.save(LunarCalendarDate.from(item));
            });
        }
        return casted;
    }

    private final LrsrCldInfoServiceClient lrsrCldInfoServiceClient;

    private final LunarInfoRepositoryService lunarInfoRepositoryService;
}
