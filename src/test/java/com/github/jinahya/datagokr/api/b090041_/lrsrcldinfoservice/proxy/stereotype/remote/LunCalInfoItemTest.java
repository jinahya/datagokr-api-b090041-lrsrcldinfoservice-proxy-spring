package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class LunCalInfoItemTest {

    @Test
    void testGetItems() throws Exception {
        try (InputStream resource = getClass().getResourceAsStream("response01.xml")) {
            assertThat(resource).isNotNull();
            final List<LunCalInfoItem> items = LunCalInfoItem.getItems(new InputSource(resource));
            assertThat(items).isNotNull().hasSize(1).doesNotContainNull().allSatisfy(item -> {
            });
        }
    }
}