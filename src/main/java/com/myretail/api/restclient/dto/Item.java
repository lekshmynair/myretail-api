package com.myretail.api.restclient.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Item {
    private @Getter
    @Setter
    ProductDescription product_description;

}
