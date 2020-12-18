package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.LrsrCldInfoServiceClient;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.Application;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunarCalendarDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(
        classes = {
                Application.class,
                LunarCalendarDateServiceIT._Configuration.class
        }
)
@Slf4j
class LunarCalendarDateServiceIT {

    //    @ComponentScan(
//            basePackageClasses = {
//                    Application.class
//            }
//    )
    @Configuration
    static class _Configuration {

        @Primary
        @Bean
        LrsrCldInfoServiceClient lrsrCldInfoServiceClientSpy() {
            // https://github.com/spring-projects/spring-boot/issues/7033#issuecomment-692814078
            return Mockito.mock(LrsrCldInfoServiceClient.class,
                                MockReset.withSettings(MockReset.AFTER)
                                        .withoutAnnotations()
                                        .defaultAnswer(AdditionalAnswers.delegatesTo(lrsrCldInfoServiceClient)));
        }

        @Autowired
        private LrsrCldInfoServiceClient lrsrCldInfoServiceClient;
    }

    @PostConstruct
    private void onPostConstruct() {
        // https://stackoverflow.com/a/62809074/330457
        //lrsrCldInfoServiceClientSpy = Mockito.spy(AopTestUtils.getTargetObject(lrsrCldInfoServiceClient));
        // https://gist.github.com/phillipuniverse/4b3d39cdcceb2363a14ebdcc170d9059#file-demoapplicationtests-java-L43-L71
//        lrsrCldInfoServiceClientSpy = mock(LrsrCldInfoServiceClient.class,
//                                           AdditionalAnswers.delegatesTo(lrsrCldInfoServiceClient));
    }

    @Test
    void testGetBySolarDate() throws InterruptedException {
        final LocalDate solarDate = LocalDate.now();
        {
            final Optional<LunarCalendarDate> got = lunarInfoService.getBySolarDate(solarDate);
            assertThat(got).isNotPresent();
        }
        Thread.sleep((int) Duration.ofSeconds(10L).toMillis());
        {
            final Optional<LunarCalendarDate> got = lunarInfoService.getBySolarDate(solarDate);
            assertThat(got).isPresent().hasValueSatisfying(g -> {
                assertThat(g.getSolarDate()).isNotNull().isEqualTo(solarDate);
            });
        }
        {
            final Optional<LunarCalendarDate> got = lunarInfoService.getBySolarDate(solarDate);
            assertThat(got).isPresent().hasValueSatisfying(g -> {
                assertThat(g.getSolarDate()).isNotNull().isEqualTo(solarDate);
            });
        }
//        Thread.sleep((int) Duration.ofSeconds(10L).toMillis());
//        Mockito.verify(lrsrCldInfoServiceClient, times(1)).getLunCalInfo(solarDate);
    }

    @Test
    void testGetByLunarDate() throws InterruptedException {
        final LocalDate lunarDate = LocalDate.now();
        {
            final Optional<LunarCalendarDate> got = lunarInfoService.getByLunarDate(lunarDate);
            assertThat(got).isNotPresent();
        }
        Thread.sleep((int) Duration.ofSeconds(10L).toMillis());
        {
            final Optional<LunarCalendarDate> got = lunarInfoService.getByLunarDate(lunarDate);
            assertThat(got).isPresent().hasValueSatisfying(g -> {
                assertThat(g.getLunarDate()).isNotNull().isEqualTo(lunarDate);
            });
        }
        {
            final Optional<LunarCalendarDate> got = lunarInfoService.getByLunarDate(lunarDate);
            assertThat(got).isPresent().hasValueSatisfying(g -> {
                assertThat(g.getLunarDate()).isNotNull().isEqualTo(lunarDate);
            });
        }
    }

    @Autowired
    private LunarInfoService lunarInfoService;

    @Autowired
    //@SpyBean
    private LrsrCldInfoServiceClient lrsrCldInfoServiceClient;
}