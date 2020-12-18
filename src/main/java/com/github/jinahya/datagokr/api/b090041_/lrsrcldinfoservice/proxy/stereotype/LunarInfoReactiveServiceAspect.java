package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.LrsrCldInfoServiceReactiveClient;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LunarInfoReactiveServiceAspect {

    @Around(value = "execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarInfoReactiveService.getBySolarDate(..))"
                    + " && args(solarDate)")
    public Object aroundGetBySolarDate(final ProceedingJoinPoint joinPoint, final LocalDate solarDate)
            throws Throwable {
        final Object result = joinPoint.proceed();
        @SuppressWarnings({"unchecked"})
        final Mono<LunarCalendarDate> casted = (Mono<LunarCalendarDate>) result;
        return casted.switchIfEmpty(Mono.fromSupplier(() -> {
            final CompletableFuture<Void> completableFuture = CompletableFuture.<Void>runAsync(() -> {
                try {
                    lrsrCldInfoServiceReactiveClient.getLunCalInfo(solarDate)
                            .map(LunarCalendarDate::from)
                            .doOnNext(lunarInfoRepositoryService::save)
                            .block();
                } catch (final Exception e) {
                    log.error("failed to get and save lunar info", e);
                }
            });
            return null;
        }));
    }

    @Around(value = "execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LunarInfoReactiveService.getBySolarDate(..))"
                    + " && args(lunarDate)")
    public Object aroundGetByLunarDate(final ProceedingJoinPoint joinPoint, final LocalDate lunarDate)
            throws Throwable {
        final Object result = joinPoint.proceed();
        @SuppressWarnings({"unchecked"})
        final Mono<LunarCalendarDate> casted = (Mono<LunarCalendarDate>) result;
        return casted.switchIfEmpty(Mono.fromSupplier(() -> {
            final CompletableFuture<Void> completableFuture = CompletableFuture.<Void>runAsync(() -> {
                try {
                    lrsrCldInfoServiceReactiveClient.getSolCalInfo(lunarDate)
                            .map(LunarCalendarDate::from)
                            .doOnNext(lunarInfoRepositoryService::save)
                            .block();
                } catch (final Exception e) {
                    log.error("failed to get and save solar info", e);
                }
            });
            return null;
        }));
    }

    private final LrsrCldInfoServiceReactiveClient lrsrCldInfoServiceReactiveClient;

    private final LunarInfoRepositoryService lunarInfoRepositoryService;
}
