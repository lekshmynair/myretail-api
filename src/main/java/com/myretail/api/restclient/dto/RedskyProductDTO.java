package com.myretail.api.restclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedskyProductDTO {
    protected Integer tcin;
    protected RedskyItemDTO item;
}
