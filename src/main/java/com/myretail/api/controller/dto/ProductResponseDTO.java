package com.myretail.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Response DTO for the getproduct controller method
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDTO {
    Integer id;
    String name;

    @JsonProperty("current_price")
    PriceDTO currentPrice;

    List<WarningDTO> warnings;
}
