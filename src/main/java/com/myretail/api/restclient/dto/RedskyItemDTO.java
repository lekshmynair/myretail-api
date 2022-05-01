package com.myretail.api.restclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedskyItemDTO {
    @JsonProperty("product_description")
    protected RedskyProductDescriptionDTO productDescription;
}
