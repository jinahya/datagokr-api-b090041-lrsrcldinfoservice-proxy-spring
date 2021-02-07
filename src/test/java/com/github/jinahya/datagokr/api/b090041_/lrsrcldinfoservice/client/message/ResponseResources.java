package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public final class ResponseResources {

    private static Response unmarshal(final URL resource) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(Response.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Response) unmarshaller.unmarshal(resource);
    }

    private static final List<Response> RESPONSES;

    static {
        final List<Response> responses = new ArrayList<>();
        try {
            assert ResponseResources.class.getResource("response_getLunCalInfo_2021_01.xml") != null;
            responses.add(unmarshal(ResponseResources.class.getResource("response_getLunCalInfo_2021_01.xml")));
        } catch (final JAXBException jaxbe) {
            jaxbe.printStackTrace();
        }
        RESPONSES = Collections.unmodifiableList(responses);
    }

    public static List<Response> responses() {
        return RESPONSES;
    }

    public static Stream<Item> items() {
        return responses().stream().flatMap(r -> r.getBody().getItems().stream());
    }

    private ResponseResources() {
        throw new AssertionError("instantiation is not allowed");
    }
}
