package com.jin.ProductService.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@Builder // builder pattern
@NoArgsConstructor
public class ProductEntity {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.AUTO) // auto 從 1 開始
    private long id;

    @Column(name="product_name")
    private String name;
    @Column(name="product_price")
    private long price;
    @Column(name="product_quantity")
    private long quantity;

}
