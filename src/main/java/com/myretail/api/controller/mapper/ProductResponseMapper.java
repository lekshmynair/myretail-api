package com.myretail.api.controller.mapper;

import com.myretail.api.controller.dto.PriceResponseDTO;
import com.myretail.api.controller.dto.ProductResponseDTO;
import com.myretail.api.controller.dto.WarningDTO;
import com.myretail.api.domain.Product;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * To combine the product details returned from redsky api and price info from repo method into product repsonse DTO
 */
public class ProductResponseMapper {

    public static ProductResponseDTO mapToDTO(Product product) {
        ProductResponseDTO prodResponse = new ProductResponseDTO();
        prodResponse.setId(product.getId());
        prodResponse.setName(product.getName());

        if (product.getPrice() != null) {
            prodResponse.setCurrentPrice(new PriceResponseDTO(product.getPrice().getValue(),
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
        }
        return prodResponse;
    }
}

