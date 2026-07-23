package com.product.product.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.product.product.model.Product;

public interface ProductRepository extends MongoRepository<Product,String>{
    List<Product> findByActiveTrue();

    List<Product> findByActiveFalse();

    List<Product> findByCategoriesContaining(String category);

    List<Product> findByCategoriesContainingAndActive(String category, boolean active);

    List<Product> findByIdIn(List<String> ids);

}
