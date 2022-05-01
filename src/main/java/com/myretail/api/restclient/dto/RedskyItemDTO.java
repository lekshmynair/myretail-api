package com.myretail.api.restclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RedskyItemDTO {
    @JsonProperty("product_description")
    private RedskyProductDescriptionDTO productDescription;
}
