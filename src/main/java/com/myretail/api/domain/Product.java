package com.myretail.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Product domain info
 */
@Data
public class Product {
    protected Integer id;
    protected String name;
    protected Price price;
}
