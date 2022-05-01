package com.myretail.api.restclient;

import com.myretail.api.config.RedskyProperties;
import com.myretail.api.domain.Product;
import com.myretail.api.exception.ApplicationException;
import com.myretail.api.exception.NotFoundException;
import com.myretail.api.restclient.dto.*;
import com.myretail.api.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseCreator;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * RestClient unit test cases
 */

class ProductRestClientTest {

    String key = "3yUxt7WltYG7MFKPp7uyELi1K40ad2ys";
    String baseUrl = "https://redsky-uat.perf.target.com/redsky_aggregations/v1/redsky/case_study_v1?key=";
    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    ;
    ProductRestClient productRestClient;
    RedskyProperties redskyProperties = new RedskyProperties(baseUrl, key);

    @BeforeEach
    void init() {
        productRestClient = new ProductRestClient(restTemplate, redskyProperties);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test case to call  rest client to get product info (for a product that exists)
     */

    @Test
    void getProduct_ok() {
        Integer id = 13860428;
        String prod_title = "The Big Lebowski (Blu-ray)";
        String productURL = productRestClient.getProductURL(id);
        RedskyResposeDTO redskyResposeDTO = new RedskyResposeDTO(new RedskyDataDTO(new RedskyProductDTO(id, new RedskyItemDTO(new RedskyProductDescriptionDTO(prod_title)))));

        RedskyProductDescriptionDTO productDescriptionDTO = new RedskyProductDescriptionDTO(prod_title);
        RedskyItemDTO redskyItemDTO = new RedskyItemDTO(productDescriptionDTO);
        RedskyProductDTO redskyProductDTO = new RedskyProductDTO(id, redskyItemDTO);
        RedskyDataDTO redskyDataDTO = new RedskyDataDTO(redskyProductDTO);

        when(restTemplate.getForObject(productURL, RedskyResposeDTO.class))
                .thenReturn(new RedskyResposeDTO(redskyDataDTO));

        RedskyResposeDTO actualResponse = productRestClient.getProduct(id);
        Assertions.assertEquals(redskyResposeDTO, actualResponse);
    }

    /**
     * Test case to call  rest client to check if returns NOT found condition  (for a product that do not exist in Redsky)
     */
    @Test
    void getProduct_not_found() {
        Integer id = 1234567;

        String productURL = productRestClient.getProductURL(id);
        RedskyResposeDTO redskyResposeDTO = null;
        when(restTemplate.getForObject(productURL, RedskyResposeDTO.class)).thenThrow(new NotFoundException("not found"));
        try {
            RedskyResposeDTO actualResponse = productRestClient.getProduct(id);
            Assertions.assertTrue(false);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof NotFoundException);
        }
    }

    /**
     * Test case to call  rest client to check if returns ApplicationException  (when there are errors with redsky call )
     */
    @Test
    void getProduct_exception() {
        Integer id = 11111;
        String productURL = productRestClient.getProductURL(id);
        RedskyResposeDTO redskyResposeDTO = null;
        when(restTemplate.getForObject(productURL, RedskyResposeDTO.class)).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        try {
            RedskyResposeDTO actualResponse = productRestClient.getProduct(id);
            Assertions.assertTrue(false);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof ApplicationException);
        }
    }

}