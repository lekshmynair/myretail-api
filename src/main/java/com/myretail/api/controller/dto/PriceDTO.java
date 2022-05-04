package com.myretail.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO to return the price info - used in ProductResponseDTO
 */
@Data
@AllArgsConstructor
public class PriceDTO {
    @NotNull(message = "value should be double, cannot be null")
    Double value;
    @JsonProperty("currency_code")
    String currencyCode = "USD";
}
