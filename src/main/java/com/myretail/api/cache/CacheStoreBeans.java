package com.myretail.api.cache;

import com.myretail.api.restclient.dto.RedskyResposeDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<RedskyResposeDTO> productCache() {
        return new CacheStore<RedskyResposeDTO>(120, TimeUnit.SECONDS);
    }
}
