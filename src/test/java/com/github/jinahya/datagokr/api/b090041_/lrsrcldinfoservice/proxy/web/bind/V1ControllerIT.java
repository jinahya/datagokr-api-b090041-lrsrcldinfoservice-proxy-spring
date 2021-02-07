package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_SOL_DAY;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_SOL_MONTH;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_SOL_YEAR;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_NDJSON;

@AutoConfigureWebTestClient(timeout = "PT20S")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class V1ControllerIT {

    @Test
    void getLunCalInfo_Expected_SolYearMonMonthSolDay() {
        final LocalDate now = LocalDate.now();
        final String solYear = Integer.toString(now.getYear());
        final String solMonth = Integer.toString(now.getMonthValue());
        final String solDay = Integer.toString(now.getDayOfMonth());
        final FluxExchangeResult<Response> result = testClient
                .get()
                .uri(b -> b.path("/v1/getLunCalInfo")
                        .queryParam(QUERY_PARAM_NAME_SOL_YEAR, solYear)
                        .queryParam(QUERY_PARAM_NAME_SOL_MONTH, solMonth)
                        .queryParam(QUERY_PARAM_NAME_SOL_DAY, solDay)
                        .build())
                .accept(current().nextBoolean() ? APPLICATION_JSON : APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Response.class);
        StepVerifier.create(result.getResponseBody())
                .thenConsumeWhile(r -> {
                    return true;
                })
                .verifyComplete();
    }

    @Autowired
    private WebTestClient testClient;
}