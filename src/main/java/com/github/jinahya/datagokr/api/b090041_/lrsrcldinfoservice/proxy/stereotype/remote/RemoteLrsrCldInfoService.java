package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.xml.sax.InputSource;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotNull;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Validated
@Service
@Slf4j
public class RemoteLrsrCldInfoService {

    public static final String BASE_URL = "http://apis.data.go.kr/B090041/openapi/service/LrsrCldInfoService";

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    static {
        DOCUMENT_BUILDER_FACTORY.setNamespaceAware(false);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public RemoteLrsrCldInfoService() {
        super();
        webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .exchangeStrategies(
                        ExchangeStrategies.builder()
                                .codecs(configurer -> {
                                    configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder());
                                })
                                .build()
                )
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Mono<LunCalInfoItem> getLunCalInfo(@NotNull final LocalDate solarDate) {
        return webClient
                .get()
                .uri(b -> {
                    return b.pathSegment("getLunCalInfo")
                            .queryParam("ServiceKey", serviceKey)
                            .queryParam("solYear", format("%1$04d", solarDate.getYear()))
                            .queryParam("solMonth", format("%1$02d", solarDate.getMonthValue()))
                            .queryParam("solDay", format("%1$02d", solarDate.getDayOfMonth()))
                            .build();
                })
                .retrieve()
                .bodyToMono(ByteArrayResource.class)
                .<LunCalInfoItem>handle((r, s) -> {
                    try {
                        try (InputStream stream = r.getInputStream()) {
                            try {
                                final List<LunCalInfoItem> items = LunCalInfoItem.getItems(new InputSource(stream));
                                if (items.isEmpty()) {
                                    s.error(new RuntimeException("no items in the document"));
                                }
                                assert items.size() == 1;
                                s.next(items.get(0));
                            } catch (final Exception e) {
                                log.error("failed to parse items from the document", e);
                                s.error(e);
                            }
                        }
                    } catch (IOException ioe) {
                        log.error("failed to open the content stream", ioe);
                        s.error(ioe);
                    }
                })
                .doOnNext(v -> {
                    assert solarDate.equals(v.getSolarDate());
                })
                ;
    }

    public void getSolCalInfo(@NotNull final Year year, @NotNull final Month month,
                              @Nullable final Integer dayOfMonth,
                              @NotNull final Sinks.Many<Response.Body.Item> itemsSink) {
        for (final AtomicInteger pageNo = new AtomicInteger(); ; ) {
            final Mono<Response> mono = webClient
                    .get()
                    .uri(b -> {
                        return b.pathSegment("getSolCalInfo")
                                .queryParam("ServiceKey", serviceKey)
                                .queryParam("lunYear", format("%1$04d", year.getValue()))
                                .queryParam("lunMonth", format("%1$02d", month.getValue()))
                                .queryParamIfPresent("lunDay", ofNullable(dayOfMonth).map(v -> format("%1$02d", v)))
                                .queryParam("pageNo", pageNo.getAndIncrement())
                                .build();
                    })
                    .retrieve()
                    .bodyToMono(Response.class)
                    .log();
            final Response response;
            try {
                response = mono.block();
            } catch (final RuntimeException re) {
                final Throwable e = re.getCause();
                log.error("failed to retrieve", e);
                itemsSink.emitError(e, (t, r) -> {
                    log.error("failed to emit error; type: {}, result: {}", t, r);
                    return false;
                });
                return;
            }
            log.debug("response: {}", response);
            assert response != null;
            {
                final Response.Header header = response.getHeader();
                if (!header.isResultCodeSuccess()) {
                    log.error("unsuccessful result: {}", header);
                    break;
                }
            }
            final Response.Body body = response.getBody();
            final List<Response.Body.Item> items = body.getItems();
            items.forEach(i -> {
                itemsSink.emitNext(i, (t, r) -> {
                    log.error("failed to emit next; type: {}, result: {}", t, r);
                    return false;
                });
            });
            if (items.isEmpty()) {
                break;
            }
        }
        itemsSink.emitComplete((t, r) -> {
            log.error("failed to emit complete; type: {}, r: {}", t, r);
            return false;
        });
    }

    private final WebClient webClient;

    @Value("${datagokr.api.b090041.lrsrcldinfoservice.service-key}")
    private String serviceKey;
}
