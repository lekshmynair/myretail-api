package com.myretail.api.repository.entity;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("price")
@Data
public class PriceEntity {
    @PrimaryKey
    private Integer id;
    private Double price;
    private String currency;

}
