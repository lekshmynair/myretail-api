package com.myretail.api.domain;

import lombok.*;

@Data
@AllArgsConstructor
public class Price {
    Integer productId;
    Double value;
    String currencyCode;
}
