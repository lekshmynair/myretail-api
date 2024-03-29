package com.myretail.api.controller;

import com.myretail.api.controller.dto.ErrorDTO;
import com.myretail.api.controller.dto.PriceDTO;
import com.myretail.api.controller.dto.ProductResponseDTO;
import com.myretail.api.controller.mapper.ProductMapper;
import com.myretail.api.domain.Price;
import com.myretail.api.domain.Product;
import com.myretail.api.annotation.MyRetailLoggable;
import com.myretail.api.service.ProductService;
import io.micrometer.core.annotation.Timed;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for the Product APIs
 */
@RestController

public class ProductController {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    ProductService prodService;

    public ProductController(ProductService prodService) {
        this.prodService = prodService;
    }

    /**
     * @param id id - product id
     * @return ProductResponse DTO that returns product info as well as the price info
     * @throws Throwable
     */
    @Operation(summary = "Get Product info by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Product not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "206", description = "Partial response", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error retrieving product", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class))})})

    @GetMapping("/products/v1/{id}")
    @MyRetailLoggable
    @Timed(value = "myretail.api.get")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable(value = "id") Integer id) throws Throwable {
        Product prod = prodService.getProductById(id);
        ProductResponseDTO dto = ProductMapper.mapToDTO(prod);
        if (dto.getCurrentPrice() == null) {
            return new ResponseEntity(dto, HttpStatus.PARTIAL_CONTENT);
        }
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/products/v1/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseBody
    @MyRetailLoggable
    @Timed(value = "myretail.api.put")
    public ProductResponseDTO updateProduct(@Valid @RequestBody PriceDTO priceDto,
                                            @PathVariable(value = "id") Integer id)
            throws Throwable {
        if (priceDto.getCurrencyCode() == null) priceDto.setCurrencyCode("USD");

        Price price = ProductMapper.mapPriceRequestToDomain(id, priceDto);

        Product prod = prodService.updatePrice(price);
        ProductResponseDTO dto = ProductMapper.mapToDTO(prod);
        return dto;
    }
}
