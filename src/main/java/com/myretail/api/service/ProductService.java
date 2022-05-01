package com.myretail.api.service;

import com.myretail.api.domain.Price;
import com.myretail.api.domain.Product;
import com.myretail.api.exception.ApplicationException;
import com.myretail.api.exception.NotFoundException;
import com.myretail.api.repository.ProductRepository;
import com.myretail.api.repository.entity.PriceEntity;
import com.myretail.api.restclient.ProductRestClient;
import com.myretail.api.restclient.RestClientDelegate;
import com.myretail.api.restclient.dto.RedskyResposeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * This
 */
@Service
public class ProductService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    ProductRepository productRepository;
    RestClientDelegate restClientDelegate;

    public ProductService(ProductRepository productRepository, RestClientDelegate restClientDelegate) {
        this.restClientDelegate = restClientDelegate;
        this.productRepository = productRepository;
    }

    /**
     *
     * @param id : product id
     * @return Product details
     */
    public Product getProductById(Integer id) {
        String productName = null;
        Optional<PriceEntity> priceEntity = null;

        //Call redsky restclient & getPrice in parallel to reduce response time
        CompletableFuture<String> restClientFuture = CompletableFuture.supplyAsync(() ->
                restClientDelegate.getProductName(id));
        CompletableFuture<Optional<PriceEntity>> priceFuture = CompletableFuture.supplyAsync(() ->
                productRepository.findById(id));

        try {
            productName = restClientFuture.get();
            priceEntity = priceFuture.get();
        } catch (Throwable e) {
            log.error("Error getting product details for {} {}", id, e);
            if (e.getCause() instanceof NotFoundException) {  //item not found in redsky
                throw new NotFoundException("Product not found");
            } else {
                throw new ApplicationException("Error retrieving product details", e); //error calling redsky or db
            }
        }
        return mapToDomain(id, productName, priceEntity); //convert DTOs to domain
    }

    private Product mapToDomain(Integer id, String productName, Optional currPrice) {
        Product prod = new Product();
        prod.setId(id);
        prod.setName(productName);
        if (currPrice != null && currPrice.isPresent()) {
            PriceEntity priceEntity = (PriceEntity) currPrice.get();
            prod.setPrice(new Price(id, priceEntity.getPrice(), priceEntity.getCurrency()));
        }
        return prod;
    }
}

