package com.myretail.api.tests;

import com.myretail.api.MyretailApiApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.hamcrest.CoreMatchers.is;


import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MyretailApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class MyretailIntegrationTest {

    @Autowired
    private MockMvc mvc;

    static Integer PRODUCT_WITH_PRICE = 13860428;
    static Integer PRODUCT_WITH_NO_PRICE = 13264003;
    static Integer PRODUCT_INVALID = 12334455;

    @Test
    public void test_product_found_ok() throws Exception {
        URI uri = new URI("/v1/products/" + PRODUCT_WITH_PRICE);

        mvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(PRODUCT_WITH_PRICE)));
    }

    @Test
    public void test_product_found_price_not_found() throws Exception {
        URI uri = new URI("/v1/products/" + PRODUCT_WITH_NO_PRICE);

        mvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPartialContent())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(PRODUCT_WITH_NO_PRICE)));
    }

    @Test
    public void test_product_not_found() throws Exception {
        URI uri = new URI("/v1/products/" + PRODUCT_INVALID);

        mvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
            //    .andExpect(jsonPath("$.id", is(PRODUCT_INVALID)));
    }

}
