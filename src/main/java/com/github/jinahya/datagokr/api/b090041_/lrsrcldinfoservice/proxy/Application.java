package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackageClasses = {
                Application.class,
                com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client._NoOp.class
        }
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
