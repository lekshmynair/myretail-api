package com.myretail.api.controller;

import com.myretail.api.domain.Product;
import com.myretail.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class ProductController {
    Logger log = Logger.getLogger(ProductService.class.getName());
    ProductService prodService;

    public ProductController(ProductService prodService) {
        this.prodService = prodService;
    }

    @GetMapping("/v1/products")
    public Product getProduct(@RequestParam(value = "id") int id) throws Throwable{
        log.info("Calling product service using parms, id = " + id);
        return prodService.getProduct(id);
    }
}
