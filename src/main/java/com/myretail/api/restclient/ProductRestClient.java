package com.myretail.api.restclient;

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

@Component
public class ProductRestClient {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    RestTemplate restTemplate;

    public ProductRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${redsky.baseUrl}")
    private String baseURL;

    @Value("${redsky.key}")
    private String key;

    @Cacheable("products")
    public RedskyResposeDTO getProduct(Integer id) {
        log.info("calling product api");
        RedskyResposeDTO resp = null;
        try {
            resp = restTemplate.getForObject(getProductURL(id), RedskyResposeDTO.class);
            log.info("API response : " + resp);
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


    private String getProductURL(Integer id) {
        StringBuilder prodURL = new StringBuilder();
        prodURL.append(baseURL).append(key).append("&tcin=").append(id);
        log.info("ProdURL : " + prodURL.toString());
        return prodURL.toString().trim();
    }
}
