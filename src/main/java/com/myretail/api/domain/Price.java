package com.myretail.api.domain;

import lombok.*;

/**
 * Price - Domain info
 */
@Data
@AllArgsConstructor
public class Price {
    protected Integer productId;
    protected Double value;
    protected String currencyCode;
}
