package com.jin.ProductService.controller;


import com.jin.ProductService.entity.ProductEntity;
import com.jin.ProductService.model.ProductRequest;
import com.jin.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //響應 restful api
@RequestMapping("/product")
public class ProductController {
    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
        long id = productService.addProduct(productRequest);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
