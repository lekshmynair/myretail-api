package com.myretail.api.controller.mapper;

import com.myretail.api.controller.dto.PriceDTO;
import com.myretail.api.controller.dto.ProductResponseDTO;
import com.myretail.api.controller.dto.WarningDTO;
import com.myretail.api.domain.Price;
import com.myretail.api.domain.Product;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * To combine the product details returned from redsky api and price info from repo method into product repsonse DTO
 */
public class ProductMapper {

    public static ProductResponseDTO mapToDTO(Product product) {
        ProductResponseDTO prodResponse = new ProductResponseDTO();
        prodResponse.setId(product.getId());
        prodResponse.setName(product.getName());

        if (product.getPrice() != null) {
            prodResponse.setCurrentPrice(new PriceDTO(product.getPrice().getValue(),
                    product.getPrice().getCurrencyCode()));
        }

        if (product.getId() == null || product.getPrice() == null) { //partial response scenario (item found, price missing)
            List<WarningDTO> warnings = new ArrayList<>();
            if (product.getId() == null) {
                warnings.add(new WarningDTO(HttpStatus.PARTIAL_CONTENT.value(), "Product not found"));
            }
            if (product.getPrice() == null) {
                warnings.add(new WarningDTO(HttpStatus.PARTIAL_CONTENT.value(), "Price not found"));
            }
            prodResponse.setWarnings(warnings);
        }
        return prodResponse;
    }

    public static Price mapPriceRequestToDomain(Integer productId, PriceDTO priceDto) {
        return new Price(productId, priceDto.getValue(), priceDto.getCurrencyCode());
    }

}

