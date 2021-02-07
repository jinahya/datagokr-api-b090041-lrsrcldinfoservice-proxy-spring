package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.LrsrCldInfoServiceClient;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Item;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.Application;
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
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = {
                Application.class,
                LunarCalendarServiceIT._Configuration.class
        }
)
@Slf4j
class LunarCalendarServiceIT {

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

    // -----------------------------------------------------------------------------------------------------------------
    @PostConstruct
    private void onPostConstruct() {
        // https://stackoverflow.com/a/62809074/330457
        //lrsrCldInfoServiceClientSpy = Mockito.spy(AopTestUtils.getTargetObject(lrsrCldInfoServiceClient));
        // https://gist.github.com/phillipuniverse/4b3d39cdcceb2363a14ebdcc170d9059#file-demoapplicationtests-java-L43-L71
//        lrsrCldInfoServiceClientSpy = mock(LrsrCldInfoServiceClient.class,
//                                           AdditionalAnswers.delegatesTo(lrsrCldInfoServiceClient));
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void getItemsForLunarDate() {
        final LocalDate now = LocalDate.now();
        final List<Item> items = lunarCalendarService.getItemsForLunarDate(
                Year.from(now), Month.from(now), now.getDayOfMonth());
        assertThat(items).isNotNull();
    }

    @Test
    void getItemsForLunarYearMonth() {
        final YearMonth lunarYearMonth = YearMonth.now();
        final List<Item> items = lunarCalendarService.getItemsForLunarYearMonth(lunarYearMonth);
        assertThat(items)
                .isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .isSortedAccordingTo(Item.COMPARING_IN_LUNAR)
                .allSatisfy(i -> {
                    assertThat(i.getLunarYear().getValue()).isEqualTo(lunarYearMonth.getYear());
                    assertThat(i.getLunarMonth()).isNotNull().isEqualTo(lunarYearMonth.getMonth());
                })
        ;
    }

    @Test
    void getItemsForLunarYearMonth_concurrent() throws InterruptedException {
        final int count = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(count);
        final YearMonth lunarYearMonth = YearMonth.now();
        final CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                latch.countDown();
                try {
                    latch.await();
                } catch (final InterruptedException ie) {
                    ie.printStackTrace();
                }
                final List<Item> items = lunarCalendarService.getItemsForLunarYearMonth(lunarYearMonth);
            });
        }
        executorService.shutdown();
        final boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    void getItemsForLunarYear_() {
        final Year lunarYear = Year.now().minus(1L, ChronoUnit.YEARS);
        final List<Item> items = lunarCalendarService.getItemsForLunarYear(lunarYear);
        assertThat(items)
                .isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .isSortedAccordingTo(Item.COMPARING_IN_LUNAR)
                .allSatisfy(i -> {
                    assertThat(i.getLunarYear()).isEqualTo(lunarYear);
                })
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void getItemsForSolarDate_() {
        final LocalDate solarDate = LocalDate.now();
        final List<Item> items = lunarCalendarService.getItemsForSolarDate(solarDate);
        assertThat(items)
                .isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .isSortedAccordingTo(Item.COMPARING_IN_SOLAR)
                .allSatisfy(i -> {
                    assertThat(i).isNotNull();
                })
        ;
    }

    @Test
    void getItemsForSolarYearMonth_() {
        final YearMonth solarYearMonth = YearMonth.now();
        final List<Item> items = lunarCalendarService.getItemsForSolarYearMonth(solarYearMonth);
        assertThat(items)
                .isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .isSortedAccordingTo(Item.COMPARING_IN_SOLAR)
                .allSatisfy(i -> {
                    assertThat(i.getSolarYear().getValue()).isEqualTo(solarYearMonth.getYear());
                    assertThat(i.getSolarMonth()).isNotNull().isEqualTo(solarYearMonth.getMonth());
                })
        ;
    }

    @Test
    void getItemsForSolarYear_() {
        final Year solarYear = Year.now().minus(1L, ChronoUnit.YEARS);
        final List<Item> items = lunarCalendarService.getItemsForSolarYear(solarYear);
        assertThat(items)
                .isNotNull()
                .isNotEmpty()
                .doesNotContainNull()
                .isSortedAccordingTo(Item.COMPARING_IN_SOLAR)
                .allSatisfy(i -> {
                    assertThat(i.getSolarYear()).isEqualTo(solarYear);
                })
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    private LunarCalendarService lunarCalendarService;

    @Autowired
    //@SpyBean
    private LrsrCldInfoServiceClient lrsrCldInfoServiceClient;
}