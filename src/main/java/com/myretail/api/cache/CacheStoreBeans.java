package com.myretail.api.cache;

import com.myretail.api.restclient.dto.ProductResposeDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<ProductResposeDTO> productCache() {
        return new CacheStore<ProductResposeDTO>(120, TimeUnit.SECONDS);
    }
}
