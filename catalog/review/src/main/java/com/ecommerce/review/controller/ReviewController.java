// controller/ReviewController.java
package com.ecommerce.review.controller;

import com.ecommerce.review.dto.ReviewDetailResponse;
import com.ecommerce.review.model.Review;
import com.ecommerce.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        return new ResponseEntity<>(reviewService.createReview(review), HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getByProduct(@PathVariable String productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ReviewDetailResponse> getReviewDetail(@PathVariable String id) {
        return ResponseEntity.ok(reviewService.getReviewDetail(id));
    }
}