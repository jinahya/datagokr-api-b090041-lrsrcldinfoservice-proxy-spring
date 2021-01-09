package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.context;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Slf4j
abstract class AbstractLrsrCldInfoServiceClientConfiguration {

    @AbstractLrsrCldInfoServiceClient.LrsrCldInfoServiceServiceKey
    @Bean
    public String getServiceKey() {
        return serviceKey;
    }

    @NotBlank
    @Value("${datagokr.api.b090041.lrsrcldinfoservice.client.base-url:#{T(com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient).BASE_URL_PRODUCTION}}")
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PROTECTED)
    private String baseUrl;

    @Positive
    @Value("${datagokr.api.b090041.lrsrcldinfoservice.client.connect-timeout-millis:4000}")
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PROTECTED)
    private int connectTimeoutMillis;

    @Positive
    @Value("${datagokr.api.b090041.lrsrcldinfoservice.client.write-timeout-seconds:4}")
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PROTECTED)
    private int writeTimeoutSeconds;

    @Positive
    @Value("${datagokr.api.b090041.lrsrcldinfoservice.client.read-timeout-seconds:4}")
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PROTECTED)
    private int readTimeoutSeconds;

    @NotBlank
    @Value("${datagokr.api.b090041.lrsrcldinfoservice.client.service-key}")
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PROTECTED)
    private String serviceKey;
}
