package com.myretail.api.restclient;

import com.myretail.api.exception.ApplicationException;
import com.myretail.api.exception.NotFoundException;
import com.myretail.api.restclient.dto.ProductResposeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

public class ProductRestClient {
    Logger log = Logger.getLogger(ProductRestClient.class.getName());

    @Value("${redsky.baseUrl}")
    private String baseURL;

    @Value("${redsky.key}")
    private String key;

    public ProductResposeDTO getProduct(int id) {
        log.info("calling product api");

        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate template = builder.build();
        ProductResposeDTO resp = null;
        try {
            resp = template.getForObject(getProductURL(id), ProductResposeDTO.class);
            log.info("API response : " + resp);
        } catch (HttpClientErrorException e) {
            log.severe("Error reading  weather info for id = " + id + ", Status code: " + e.getRawStatusCode());
            log.severe("Stacktrace: " + e.getStackTrace());
            if (e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new NotFoundException("Product not found");
            } else {
                throw new ApplicationException("Error getting Product info for id " + id);
            }

        }
        return resp;
    }

    private String getProductURL(int id) {
        StringBuilder prodURL = new StringBuilder();
        prodURL.append(baseURL).append(key).append("&tcin=").append(id);
        log.info("ProdURL : " + prodURL.toString());
        return prodURL.toString().trim();
    }
}
