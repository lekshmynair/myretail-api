package com.myretail.api.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Set the URL for Redsky and key
 */
@Configuration
@ConfigurationProperties(prefix="redsky")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedskyProperties {
    String baseUrl;
    String key;
}
