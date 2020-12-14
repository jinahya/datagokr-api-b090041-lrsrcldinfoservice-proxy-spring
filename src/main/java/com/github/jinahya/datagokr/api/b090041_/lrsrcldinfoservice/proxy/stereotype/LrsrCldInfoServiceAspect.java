package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunCalInfo;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunCalInfoRepository;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote.RemoteLrsrCldInfoService;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote.LunCalInfoItem;
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
        log.debug("aroundGetForSolarDate({}, {})", proceedingJoinPoint, solarDate);
        @SuppressWarnings({"unchecked"})
        final Object proceeded = proceedingJoinPoint.proceed();
        final Optional<LunCalInfo> casted = (Optional<LunCalInfo>) proceeded;
        return casted.orElseGet(() -> {
            return remoteLrsrCldInfoService.getLunCalInfo(solarDate)
                    .map(LunCalInfoItem::toLunCalInfo)
                    .doOnNext(v -> {
                        final LunCalInfo saved = lunCalInfoRepository.save(v);
//                        final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                            itemRepository.save(v.toItem());
//                        });
                    })
                    .block();
        });
    }

    private final RemoteLrsrCldInfoService remoteLrsrCldInfoService;

    private final LunCalInfoRepository lunCalInfoRepository;
}
