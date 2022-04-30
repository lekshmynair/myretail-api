package com.myretail.api.restclient.dto;

import lombok.*;
import lombok.Data;

@Data
public class Product {
    private int tcin;
    private Item item;
}
