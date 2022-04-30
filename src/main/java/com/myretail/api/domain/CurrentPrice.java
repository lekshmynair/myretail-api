package com.myretail.api.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrentPrice {
    private @Getter
    @Setter
    Double value;
    private @Getter
    @Setter
    String currency_code;
}
