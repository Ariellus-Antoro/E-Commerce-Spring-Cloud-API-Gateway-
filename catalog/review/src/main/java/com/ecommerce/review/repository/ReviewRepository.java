// repository/ReviewRepository.java
package com.ecommerce.review.repository;

import com.ecommerce.review.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByProductId(String productId);
    List<Review> findByUserId(Integer userId);
}