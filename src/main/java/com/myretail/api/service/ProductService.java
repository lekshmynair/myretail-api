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


@Service
public class ProductService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    ProductRepository productRepository;
    RestClientDelegate restClientDelegate;

    public ProductService(ProductRepository productRepository, RestClientDelegate restClientDelegate) {
        this.restClientDelegate = restClientDelegate;
        this.productRepository = productRepository;
    }

    public Product getProductById(Integer id) {
        String productName = null;
        Optional<PriceEntity> priceEntity = null;
        //Call redsky restclient & getPrice
        CompletableFuture<String> restClientFuture = CompletableFuture.supplyAsync(() ->
                restClientDelegate.getProductName(id));
        CompletableFuture<Optional<PriceEntity>> priceFuture = CompletableFuture.supplyAsync(() ->
                productRepository.findById(id)).exceptionally(e -> {
            log.error("Error calling DB for product id : {} {}", id, e);
            throw new ApplicationException("Error retrieving price from DB", e);
        });
        try {
            productName = restClientFuture.get();
            priceEntity = priceFuture.get();
        } catch (Throwable e) {
            log.error("Error getting product details for {} {}", id, e);
            if (e.getCause() instanceof NotFoundException) {
                throw new NotFoundException("Product not found");
            }else {
                throw new ApplicationException("Error retrieving product details", e);
            }
        }
        return mapToDomain(id, productName, priceEntity);
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

