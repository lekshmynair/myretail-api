package com.myretail.api.repository.entity;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Data
public class Price {
    @PrimaryKey
    private Integer product_id;

    private Double price;
    private String currency;

}
