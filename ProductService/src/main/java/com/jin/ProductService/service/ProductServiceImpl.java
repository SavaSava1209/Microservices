package com.jin.ProductService.service;

import com.jin.ProductService.entity.ProductEntity;
import com.jin.ProductService.model.ProductRequest;
import com.jin.ProductService.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public long addProduct(ProductRequest productRequest) {
        ProductEntity productEntity = ProductEntity.builder() // productEntity 裡面用 builder pattern
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
         productRepository.save(productEntity);
        return productEntity.getId(); // id will be auto created
    }
}
