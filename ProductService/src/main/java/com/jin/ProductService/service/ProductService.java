package com.jin.ProductService.service;

import com.jin.ProductService.model.ProductRequest;
import com.jin.ProductService.model.ProductResponse;

public interface ProductService {

    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long id);

    void reduceQuantity(long id, long quantity);
}
