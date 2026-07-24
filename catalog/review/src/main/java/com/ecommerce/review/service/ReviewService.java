// service/ReviewService.java
package com.ecommerce.review.service;

import com.ecommerce.review.client.ProductClient;
import com.ecommerce.review.client.UserClient;
import com.ecommerce.review.dto.ProductResponseDTO;
import com.ecommerce.review.dto.ReviewDetailResponse;
import com.ecommerce.review.dto.UserResponseDTO;
import com.ecommerce.review.model.Review;
import com.ecommerce.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserClient userClient;
    private final ProductClient productClient;

    public ReviewService(ReviewRepository reviewRepository, UserClient userClient, ProductClient productClient) {
        this.reviewRepository = reviewRepository;
        this.userClient = userClient;
        this.productClient = productClient;
    }

    public Review createReview(Review review) {
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

        // Get All Reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Get Review By ID Mongo
    public Optional<Review> getReviewById(String id) {
        return reviewRepository.findById(id);
    }

    // Update Review (PUT)
    public Review updateReview(String id, Review details) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        review.setRating(details.getRating());
        review.setComment(details.getComment());

        return reviewRepository.save(review);
    }

    // Delete Review
    public void deleteReview(String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        reviewRepository.delete(review);
    }

    public List<Review> getReviewsByProduct(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    // Mengambil Detail Review Lintas DB (Mongo Review + SQL User + Mongo Product)
    public ReviewDetailResponse getReviewDetail(String reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        UserResponseDTO user = null;
        try {
            user = userClient.getUserById(review.getUserId());
        } catch (Exception e) { /* Fallback jika service user down */ }

        ProductResponseDTO product = null;
        try {
            product = productClient.getProductById(review.getProductId());
        } catch (Exception e) { /* Fallback jika service product down */ }

        return new ReviewDetailResponse(review, user, product);
    }
}