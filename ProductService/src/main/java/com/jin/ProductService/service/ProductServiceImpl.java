package com.jin.ProductService.service;

import com.jin.ProductService.entity.ProductEntity;
import com.jin.ProductService.model.ProductRequest;
import com.jin.ProductService.model.ProductResponse;
import com.jin.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2 // log 所有的 request
public class ProductServiceImpl implements ProductService{
    ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Start: ProductService addProduct");
        ProductEntity productEntity = ProductEntity.builder() // productEntity 裡面用 builder pattern @Build
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
         productRepository.save(productEntity);
        log.info("End: ProductService addProduct"); // productEntity will have an id
        return productEntity.getId(); // id will be auto created
    }

    @Override
    public ProductResponse getProductById(long id) {
        log.info("Start: ProductService getProductById");
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product " + id + " is not found"));

        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(productEntity, productResponse);
        log.info("End: ProductService getProductById " + productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(long id, long quantity) {
        log.info("Start: ProductService reduceQuantity with id: " + id + " quantity: " + quantity);
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("reduceQuantity: Product Not found " + id));
        if (productEntity.getQuantity() < quantity){
            throw new RuntimeException("reduceQuantity: Product not enough with id: " + id);
        }
        productEntity.setQuantity(productEntity.getQuantity() - quantity);
        productRepository.save(productEntity);
        log.info("End: ProductService reduceQuantity with id: " + id + " quantity: " + quantity);
    }
}
