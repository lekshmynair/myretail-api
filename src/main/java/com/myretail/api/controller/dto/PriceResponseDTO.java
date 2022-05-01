package com.myretail.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO to return the price info - used in ProductResponseDTO
 */
@Data
@AllArgsConstructor
public class PriceResponseDTO {
    Double value;
    @JsonProperty("currency_code")
    String currencyCode;
}
