package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableCaching
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication(
        scanBasePackageClasses = {
                Application.class,
                com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.NoOp.class
        }
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
