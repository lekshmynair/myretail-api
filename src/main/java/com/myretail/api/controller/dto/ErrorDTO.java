package com.myretail.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {
    Integer status;
    String title;
    String type;
    String detail;
}
