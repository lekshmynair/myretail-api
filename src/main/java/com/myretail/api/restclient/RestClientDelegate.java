package com.myretail.api.restclient;

import com.myretail.api.exception.ApplicationException;
import com.myretail.api.exception.NotFoundException;
import com.myretail.api.restclient.dto.RedskyResposeDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * RestClient Delegate called from ProductService
 */
@Component
public class RestClientDelegate {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    ProductRestClient productRestClient;

    public RestClientDelegate(ProductRestClient productRestClient) {
        this.productRestClient = productRestClient;
    }

    @CircuitBreaker(name = "redsky", fallbackMethod = "getProductFallback")
    public String getProductName(Integer id) {
        return productRestClient.getProduct(id).getData().getProduct().getItem().getProductDescription().getTitle();
    }

    // Circuitbreaker fallback method
    public String getProductFallback(Integer id, ApplicationException e) {
        log.error("CircuitBreaker tripped. Id= {}", id);
        throw new ApplicationException("CircuitBreaker tripped");
    }
}
