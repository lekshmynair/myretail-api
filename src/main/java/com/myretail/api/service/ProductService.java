package com.myretail.api.service;

import com.myretail.api.cache.CacheStore;
import com.myretail.api.domain.CurrentPrice;
import com.myretail.api.domain.Product;
import com.myretail.api.repository.ProductRepository;
import com.myretail.api.repository.entity.Price;
import com.myretail.api.restclient.ProductRestClient;
import com.myretail.api.restclient.dto.ProductResposeDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
public class ProductService {
    Logger log = Logger.getLogger(ProductService.class.getName());

    ProductRestClient productRestClient;
    ProductRepository productRepository;

    // CacheStore<ProductResposeDTO> productcache;

    public ProductService(ProductRestClient productRestClient, ProductRepository productRepository) {
        this.productRestClient = productRestClient;
        this.productRepository = productRepository;

    }
 /*   public ProductService(ProductRestClient productRestClient, ProductRepository productRepository, CacheStore<ProductResposeDTO> productcache) {
        this.productRestClient = productRestClient;
        this.productRepository = productRepository;
        this.productcache = productcache;
    } */
    public Product getProduct(int id) throws Throwable {
        Product prod = null;
        ProductResposeDTO prodDto = null;
        Optional<Price> price = null;

        CompletableFuture<ProductResposeDTO> future1  = CompletableFuture.supplyAsync(() -> productRestClient.getProduct(id));
        CompletableFuture<Optional<Price>> future2 = CompletableFuture.supplyAsync(() -> productRepository.findById(Integer.valueOf(id)));
       try {
           prodDto = future1.get();
           price = future2.get();
        } catch (Throwable e) {
            throw e.getCause();
        }
       /* if (price.isPresent()) {
            log.info(" id :" + price.get().getProduct_id() + ", " + price.get().getPrice() + ", " + price.get().getCurrency());
        }*/
        prod = mapToDomain(prodDto, price);
        return prod;
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
            Price price = (Price) currPrice.get();
            log.info("price = " + price);
            if (price != null) {
                log.info("price found");
                currentPrice = new CurrentPrice();
                currentPrice.setValue(price.getPrice());
                //log.info("currPrice = " + currentPrice.getValue());
                currentPrice.setCurrency_code(price.getCurrency());
                //log.info("currency = " + currentPrice.getCurrency_code());
            }
        }
        prod.setCurrent_price(currentPrice);
        //log.info("product returned :" + prod);
        return prod;
    }

}

