package com.myretail.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
public class Product {
    private Integer id;
    private String name;
    private Price price;
}
