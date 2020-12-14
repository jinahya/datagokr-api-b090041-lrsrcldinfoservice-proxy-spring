package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import org.xml.sax.InputSource;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

@Validated
@Service
@Slf4j
public class RemoteLrsrCldInfoService {

    public static final String BASE_URL = "http://apis.data.go.kr/B090041/openapi/service/LrsrCldInfoService";

    // -----------------------------------------------------------------------------------------------------------------
    public RemoteLrsrCldInfoService() {
        super();
        webClient = WebClient.builder()
                .baseUrl(BASE_URL)
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

    private final WebClient webClient;

    @Value("${datagokr.api.b090041.lrsrcldinfoservice.service-key}")
    private String serviceKey;
}
