package com.myretail.api.controller;

import com.myretail.api.controller.dto.ProductResponseDTO;
import com.myretail.api.controller.dto.WarningDTO;
import com.myretail.api.controller.mapper.ProductResponseMapper;
import com.myretail.api.domain.Product;
import com.myretail.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ProductController {
    Logger log = Logger.getLogger(ProductService.class.getName());
    ProductService prodService;

    public ProductController(ProductService prodService) {
        this.prodService = prodService;
    }

    @GetMapping("/v1/products/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable(value = "id") Integer id) throws Throwable {
        Product prod = prodService.getProductById(id);
        ProductResponseDTO dto = ProductResponseMapper.mapToDTO(prod);
        if (dto.getCurrentPrice() == null) {
            return new ResponseEntity(dto, HttpStatus.PARTIAL_CONTENT);
        }
        return new ResponseEntity(dto, HttpStatus.OK);
    }
}
