package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.context;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.LrsrCldInfoServiceReactiveClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
public class LrsrCldInfoServiceClientReactiveConfiguration extends AbstractLrsrCldInfoServiceClientConfiguration {

    @LrsrCldInfoServiceReactiveClient.LrsrCldInfoServiceWebClient
    @Bean
    HttpClient httpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis())
                .doOnConnected(c -> {
                    c.addHandlerLast(new ReadTimeoutHandler(readTimeoutSeconds()))
                            .addHandlerLast(new WriteTimeoutHandler(writeTimeoutSeconds()));
                });
    }

    @LrsrCldInfoServiceReactiveClient.LrsrCldInfoServiceWebClient
    @Bean
    ClientHttpConnector clientConnector(
            @LrsrCldInfoServiceReactiveClient.LrsrCldInfoServiceWebClient final HttpClient httpClient) {
        return new ReactorClientHttpConnector(httpClient);
    }

    @LrsrCldInfoServiceReactiveClient.LrsrCldInfoServiceWebClient
    @Bean
    ExchangeStrategies exchangeStrategies() {
        final int byteCount = 256 * 1000; // 256K by default
        return ExchangeStrategies.builder()
                .codecs(c -> {
                    c.defaultCodecs().maxInMemorySize(byteCount);
                })
                .build();
    }

    @LrsrCldInfoServiceReactiveClient.LrsrCldInfoServiceWebClient
    @Bean
    WebClient lrsrCldInfoServiceWebClient(
            @LrsrCldInfoServiceReactiveClient.LrsrCldInfoServiceWebClient final ClientHttpConnector clientConnector,
            @LrsrCldInfoServiceReactiveClient.LrsrCldInfoServiceWebClient final ExchangeStrategies exchangeStrategies) {
        return WebClient.builder()
                .clientConnector(clientConnector)
                .exchangeStrategies(exchangeStrategies)
                .filter((r, n) -> {
                    log.debug("filtering with {} and {}", r, n);
                    return n.exchange(r);
                })
                .baseUrl(AbstractLrsrCldInfoServiceClient.BASE_URL)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
                .build();
    }

    @Value("${datagokr.api.b090041.lrsrcldinfoservice.client.service-key}")
    private String serviceKey;
}
