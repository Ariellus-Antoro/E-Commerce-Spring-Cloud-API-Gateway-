package com.product.product.service;

import java.util.List;

import com.product.product.dto.ProductRequest;
import com.product.product.dto.ProductResponse;

public interface ProductService {

     ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(String id);

    List<ProductResponse> getAllProducts(String category, Boolean active);

    List<ProductResponse> getProductsByIds(List<String> ids);

    ProductResponse updateProduct(String id, ProductRequest request);

    void deleteProduct(String id);

    ProductResponse setActiveStatus(String id, boolean active);

    boolean existsById(String id);

}
