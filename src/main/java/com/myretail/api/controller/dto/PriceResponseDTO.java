package com.myretail.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceResponseDTO {
    Double value;
    @JsonProperty("currency_code")
    String currencyCode;
}
