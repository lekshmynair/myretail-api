package com.myretail.api.restclient;

import com.myretail.api.config.RedskyProperties;
import com.myretail.api.exception.ApplicationException;
import com.myretail.api.exception.NotFoundException;
import com.myretail.api.restclient.dto.RedskyResposeDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * ProductResrClient to get product info from Redsky
 */
@Component
public class ProductRestClient {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    RestTemplate restTemplate;
    RedskyProperties redskyProperties;

    public ProductRestClient(RestTemplate restTemplate, RedskyProperties redskyProperties) {
        this.restTemplate = restTemplate;
        this.redskyProperties = redskyProperties;
    }

    @Cacheable("products")
    public RedskyResposeDTO getProduct(Integer id) {
        RedskyResposeDTO resp = null;
        String url = getProductURL(id);
        try {
            resp = restTemplate.getForObject(url, RedskyResposeDTO.class);
        } catch (HttpClientErrorException e) {
            log.error("Error reading  weather info for id:{} Status code:{} Error:{}", id, e.getRawStatusCode(), e);
            if (e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new NotFoundException("Product not found");
            } else {
                throw new ApplicationException("Error getting Product info for id " + id);
            }
        }
        return resp;
    }

    public String getProductURL(Integer id) {
        StringBuilder prodURL = new StringBuilder();
        prodURL.append(redskyProperties.getBaseUrl()).append(redskyProperties.getKey()).append("&tcin=").append(id);
        return prodURL.toString().trim();
    }
}
