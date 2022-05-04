package com.myretail.api.repository.entity;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Entity for Price table
 */
@Table("price")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceEntity {
    @PrimaryKey
    protected Integer id;
    protected Double price;
    protected String currency;
}
