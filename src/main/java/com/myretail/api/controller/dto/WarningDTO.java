package com.myretail.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WarningDTO {
    Integer code;
    String description;
}
