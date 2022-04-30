package com.myretail.api.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private @Getter
    @Setter
    int id;
    private @Getter
    @Setter
    String name;
    private @Getter
    @Setter
    CurrentPrice current_price;

}
