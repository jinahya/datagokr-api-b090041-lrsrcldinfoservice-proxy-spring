package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.context;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.LrsrCldInfoServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@Slf4j
public class LrsrCldInfoServiceClientConfiguration extends AbstractLrsrCldInfoServiceClientConfiguration {

    @LrsrCldInfoServiceClient.LrsrCldInfoServiceRestTemplate
    @Bean
    RestTemplate lrsrCldInfoServiceRestTemplate() {
        return new RestTemplateBuilder()
                .additionalCustomizers(
                        t -> {
                            log.debug("1 customizing {}", t);
                        },
                        t -> {
                            log.debug("2 customizing {}", t);
                            t.setRequestFactory(new BufferingClientHttpRequestFactory(t.getRequestFactory()));
                        }
                )
                .additionalInterceptors(
                        (r, b, e) -> {
                            log.debug("1 executing with ({}, {})", r, b);
                            return e.execute(r, b);
                        },
                        (r, b, e) -> {
                            log.debug("2 executing with ({}, {})", r, b);
                            return e.execute(r, b);
                        }
                )
                .setConnectTimeout(Duration.ofMillis(connectTimeoutMillis()))
                .setReadTimeout(Duration.ofSeconds(readTimeoutSeconds()))
                .rootUri(baseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
                .build();
    }
}
