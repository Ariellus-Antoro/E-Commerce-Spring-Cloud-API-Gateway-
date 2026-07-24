// dto/ReviewDetailResponse.java (Response Gabungan Review + User SQL + Product Mongo)
package com.ecommerce.review.dto;

import com.ecommerce.review.model.Review;

public class ReviewDetailResponse {
    private Review review;
    private UserResponseDTO user;
    private ProductResponseDTO product;

    public ReviewDetailResponse() {}

    public ReviewDetailResponse(Review review, UserResponseDTO user, ProductResponseDTO product) {
        this.review = review;
        this.user = user;
        this.product = product;
    }

    public Review getReview() { return review; }
    public void setReview(Review review) { this.review = review; }

    public UserResponseDTO getUser() { return user; }
    public void setUser(UserResponseDTO user) { this.user = user; }

    public ProductResponseDTO getProduct() { return product; }
    public void setProduct(ProductResponseDTO product) { this.product = product; }
}