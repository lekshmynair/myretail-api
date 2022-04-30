package com.myretail.api.service;

import com.myretail.api.domain.CurrentPrice;
import com.myretail.api.domain.Product;
import com.myretail.api.repository.ProductRepository;
import com.myretail.api.repository.entity.Price;
import com.myretail.api.restclient.ProductRestClient;
import com.myretail.api.restclient.dto.ProductResposeDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProductService {
    Logger log = Logger.getLogger(ProductService.class.getName());

    ProductRestClient productRestClient;
    ProductRepository productRepository;


    public ProductService(ProductRestClient productRestClient, ProductRepository productRepository) {
        this.productRestClient = productRestClient;
        this.productRepository = productRepository;
    }

   /* public ProductService(ProductRestClient productRestClient) {
        this.productRestClient = productRestClient;
    } */

    public Product getProduct(int id) {
        log.info("ProductService -> inside getProduct");
        Product product = null;
        ProductResposeDTO prodDto = productRestClient.getProduct(id);
        log.info("calling repo method to get price for id = " + id);
        Optional<Price> price = productRepository.findById(Integer.valueOf(id));
        log.info("price exists: " + price.isPresent());
        if (price.isPresent()) {
            log.info(" id :" + price.get().getProduct_id() +", " + price.get().getPrice() +", " + price.get().getCurrency());
        }
        product = mapToDomain(prodDto, price);
        return product;
    }

    private Product mapToDomain(ProductResposeDTO prodDto, Optional currPrice) {
        Product prod = new Product();
        if (prodDto != null) {
            prod.setId(prodDto.getData().getProduct().getTcin());
            prod.setName(prodDto.getData().getProduct().getItem().getProduct_description().getTitle());
        }
        CurrentPrice currentPrice = null;
        if (currPrice.isPresent()) {
            log.info("price returned from db");
            Price price = (Price)currPrice.get();
            log.info("price = " + price);
            if(price != null) {
                log.info("price found");
                currentPrice = new CurrentPrice();
                currentPrice.setValue(price.getPrice());
                log.info("currPrice = " + currentPrice.getValue());
                currentPrice.setCurrency_code(price.getCurrency());
                log.info("currency = " + currentPrice.getCurrency_code());
            }
        }
        prod.setCurrent_price(currentPrice);
        log.info("product returned :" + prod);
        return prod;
    }

}
