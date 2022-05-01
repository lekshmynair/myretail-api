package com.myretail.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myretail.api.domain.Price;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDTO {
    Integer id;
    String name;

    @JsonProperty("current_price")
    PriceResponseDTO currentPrice;
    List<WarningDTO> warnings;
}
