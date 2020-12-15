package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ResponseTest {

    @Test
    void testGetItems() throws Exception {
        try (InputStream resource = getClass().getResourceAsStream("response01.xml")) {
            assertThat(resource).isNotNull();
            final JAXBContext context = JAXBContext.newInstance(Response.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final Response response = unmarshaller.unmarshal(new StreamSource(resource), Response.class).getValue();
            log.debug("response: {}", response);
            assertThat(response).isNotNull().satisfies(r -> {
                assertThat(r.getHeader()).isNotNull().satisfies(h -> {
                    assertThat(h.getResultCode()).isNotNull().isEqualTo(Response.Header.RESULT_CODE_SUCCESS);
                });
                assertThat(r.getBody()).isNotNull().satisfies(b -> {
                    assertThat(b.getItems()).isNotNull().hasSize(1).doesNotContainNull().allSatisfy(i -> {
                        log.debug("item: {}", i);
                        assertThat(i.getLunDay()).isEqualTo("30");
                    });
                });
            });
        }
    }
}