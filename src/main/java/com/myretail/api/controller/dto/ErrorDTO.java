package com.myretail.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ErrorDTO for setting the errors and messages
 */
@Data
@AllArgsConstructor
public class ErrorDTO {
    Integer status;
    String title;
    String type;
    String detail;
}
