package com.myretail.api.restclient.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ProductDescription {
    private @Getter
    @Setter
    String title;
}
