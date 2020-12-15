package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunCalInfo;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunCalInfoRepository;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote.LunCalInfoItem;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote.RemoteLrsrCldInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LrsrCldInfoServiceAspect {

    @Around(value = "execution(* com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.LrsrCldInfoService.getItemForSolarDate(..))"
                    + " && args(solarDate)")
    public Object aroundGetForSolarDate(final ProceedingJoinPoint proceedingJoinPoint, final LocalDate solarDate)
            throws Throwable {
        final Object proceeded = proceedingJoinPoint.proceed();
        @SuppressWarnings({"unchecked"})
        final Optional<LunCalInfo> casted = (Optional<LunCalInfo>) proceeded;
        if (casted.isPresent()) {
            log.debug("returning cased...");
            return casted;
        }
        log.debug("querying to the remote service...");
        return remoteLrsrCldInfoService.getLunCalInfo(solarDate)
                .map(LunCalInfoItem::toLunCalInfo)
                .doOnNext(v -> {
                    log.debug("saving {}", v);
                    final LunCalInfo saved = lunCalInfoRepository.save(v);
                    log.debug("saved: {}", saved);
//                    final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                        final LunCalInfo saved = lunCalInfoRepository.save(v.toItem());
//                    });
                })
                .blockOptional();
    }

    private final RemoteLrsrCldInfoService remoteLrsrCldInfoService;

    private final LunCalInfoRepository lunCalInfoRepository;
}
