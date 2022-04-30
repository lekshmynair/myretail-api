package com.myretail.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    private int id;
    private String name;
    private CurrentPrice current_price;

}
