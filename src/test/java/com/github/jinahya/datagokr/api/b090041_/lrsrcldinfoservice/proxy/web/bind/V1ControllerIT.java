package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.message.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_SOL_DAY;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_SOL_MONTH;
import static com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.client.AbstractLrsrCldInfoServiceClient.QUERY_PARAM_NAME_SOL_YEAR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class V1ControllerIT {

    @Test
    void getLunCalInfo_Expected_SolYearMonMonthSolDay() throws Exception {
        final LocalDate now = LocalDate.now();
        final String solYear = Integer.toString(now.getYear());
        final String solMonth = Integer.toString(now.getMonthValue());
        final String solDay = Integer.toString(now.getDayOfMonth());
        final MvcResult mvcResult = mockMvc
                .perform(get("/v1/getLunCalInfo")
                                 .queryParam(QUERY_PARAM_NAME_SOL_YEAR, solYear)
                                 .queryParam(QUERY_PARAM_NAME_SOL_MONTH, solMonth)
                                 .queryParam(QUERY_PARAM_NAME_SOL_DAY, solDay)
                                 .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        final String content = mvcResult.getResponse().getContentAsString();
        final List<Response> response = objectMapper.readValue(content, new TypeReference<List<Response>>() {
        });
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
}