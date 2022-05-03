package com.myretail.api.service;

import com.myretail.api.domain.Product;
import com.myretail.api.exception.ApplicationException;
import com.myretail.api.exception.NotFoundException;
import com.myretail.api.repository.ProductRepository;
import com.myretail.api.repository.entity.PriceEntity;
import com.myretail.api.restclient.ProductRestClient;
import com.myretail.api.restclient.RestClientDelegate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.cassandra.CassandraReadTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ProductService unit test cases
 */
class ProductServiceTest {
    RestClientDelegate restClientDelegate = Mockito.mock(RestClientDelegate.class);
    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, restClientDelegate);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test case to validate  happy path - returns product info from redsky  and price from db
     */
    @Test
    void getProductById_product_price_present() {
        Integer id = 13860428;
        String prodname_13860428 = "The Big Lebowski (Blu-ray)";
        PriceEntity prodPrice = new PriceEntity(13860428, 13.49, "USD");
        Optional<PriceEntity> price = Optional.of(prodPrice);

        when(restClientDelegate.getProductName(id)).thenReturn(prodname_13860428);
        when(productRepository.findById(id)).thenReturn(price);

        Product product = productService.getProductById(id);
        Assertions.assertEquals(product.getId(), 13860428);
        Assertions.assertEquals(product.getPrice().getValue(), 13.49);
    }

    /**
     * Test case to test scenario where product info is returned if product exists in redsky with no proce when price not setup
     */
    @Test
    void getProductById_product_present_price_not_present() {
        Integer id = 13264003;
        String prodname_13264003 = "Jif Natural Creamy Peanut Butter - 40oz";
        Optional<PriceEntity> prodPrice = Optional.empty();

        when(restClientDelegate.getProductName(id)).thenReturn(prodname_13264003);
        when(productRepository.findById(id)).thenReturn(prodPrice);
        Product product = productService.getProductById(id);
        Assertions.assertEquals(product.getId(), id);
        Assertions.assertEquals(product.getPrice(), null);
    }

    /**
     * Test case to validate that service returns not found exception when product does not exist in redsky
     */
    @Test
    void getProductById_product_not_present() {
        Integer id = 123456;
        String prodname_123456 = null;
        Optional<PriceEntity> prodPrice = null;

        when(restClientDelegate.getProductName(id)).thenThrow(new NotFoundException("not found"));
        when(productRepository.findById(id)).thenReturn(prodPrice);
        try {
            Product product = productService.getProductById(id);
            Assertions.assertTrue(false);
        }catch (Exception e) {
            Assertions.assertTrue(e instanceof NotFoundException);
        }
    }

    /**
     * Test case to validate that when exceptions thrown from redsky or from repo method, it throws Application exception
     */
    @Test
    void getProductById_application_exception() {
    Integer id = 123456;
        String prodname_123456 = null;
        Optional<PriceEntity> prodPrice = null;

        when(restClientDelegate.getProductName(id)).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        when(productRepository.findById(id)).thenThrow(new RuntimeException());
        try {
            Product product = productService.getProductById(id);
            Assertions.assertTrue(false);
        }catch (Exception e) {
            Assertions.assertTrue(e instanceof ApplicationException);
        }
    }
}