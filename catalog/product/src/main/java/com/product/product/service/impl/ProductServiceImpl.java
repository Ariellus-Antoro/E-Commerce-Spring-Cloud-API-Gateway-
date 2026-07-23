package com.product.product.service.impl;

import com.product.product.dto.ProductRequest;
import com.product.product.dto.ProductResponse;
import com.product.product.exception.ResourceNotFoundException;
import com.product.product.model.Product;
import com.product.product.repository.ProductRepository;
import com.product.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
       Product product = Product.builder()
        .name(request.getName())
        .description(request.getDescription())
        .basePrice(request.getBasePrice())
        .categories(request.getCategories())
        .active(request.getActive() == null || request.getActive())
        .createdAt(Instant.now())
        .updateAt(Instant.now())
        .build();

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse getProductById(String id) {
        return mapToResponse(findProductOrThrow(id));
    }

    @Override
    public List<ProductResponse> getAllProducts(String category, Boolean active) {
        List<Product> products;

        if (category != null && active != null) {
            products = productRepository.findByCategoriesContainingAndActive(category, active);
        } else if (category != null) {
            products = productRepository.findByCategoriesContaining(category);
        } else if (active != null) {
            products = active ? productRepository.findByActiveTrue() : productRepository.findByActiveFalse();
        } else {
            products = productRepository.findAll();
        }

        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByIds(List<String> ids) {
        return productRepository.findByIdIn(ids).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product product = findProductOrThrow(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBasePrice(request.getBasePrice());
        product.setCategories(request.getCategories());
        if (request.getActive() != null) {
            product.setActive(request.getActive());
}
product.setUpdateAt(Instant.now());

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(String id) {
        Product product = findProductOrThrow(id);
        productRepository.delete(product);
    }

    @Override
    public ProductResponse setActiveStatus(String id, boolean active) {
        Product product = findProductOrThrow(id);
        product.setActive(active);
        product.setUpdateAt(Instant.now());
        return mapToResponse(productRepository.save(product));
    }

    @Override
    public boolean existsById(String id) {
        return productRepository.existsById(id);
    }

    private Product findProductOrThrow(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product tidak ditemukan dengan id: " + id));
    }

    private ProductResponse mapToResponse(Product product) {
    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .basePrice(product.getBasePrice())
            .categories(product.getCategories())
            .active(product.getActive())
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdateAt())
            .build();
}
}