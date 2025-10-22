package com.onandhome.review;

import com.onandhome.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 리뷰 엔티티용 Repository
 * JPA가 자동으로 CRUD 구현해줌
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
