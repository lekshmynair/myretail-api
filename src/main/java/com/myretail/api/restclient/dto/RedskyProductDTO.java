package com.myretail.api.restclient.dto;

import lombok.Data;

@Data
public class RedskyProductDTO {
    private Integer tcin;
    private RedskyItemDTO item;
}
