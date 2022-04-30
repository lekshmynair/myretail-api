package com.myretail.api.restclient.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Product {
    private @Getter
    @Setter
    int tcin;
    private @Getter
    @Setter
    Item item;

}
