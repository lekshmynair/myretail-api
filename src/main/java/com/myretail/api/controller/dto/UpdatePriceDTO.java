package com.myretail.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdatePriceDTO {
    @JsonProperty("current_price")
    Double price;
    @JsonProperty("current_code")
    String currency;
}
