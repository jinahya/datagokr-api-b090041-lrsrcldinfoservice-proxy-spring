package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.LrsrCldInfoServiceReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient(timeout = "PT20S")
@WebFluxTest(controllers = {V1Controller.class})
class V1ControllerTest {

    @MockBean
    private LrsrCldInfoServiceReactiveClient serviceClient;

    @Autowired
    private WebTestClient webClient;
}