package com.myretail.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO to show warnings
 */
@Data
@AllArgsConstructor
public class WarningDTO {
    Integer code;
    String description;
}
