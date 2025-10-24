package com.onandhome.review;

import com.onandhome.review.entity.Review;
import com.onandhome.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 리뷰 레포지토리
 * user 엔티티와의 join 없이 기본 조회만 수행
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // findAllWithUser() 제거 → 기본 findAll() 사용

    /**
     * 상품 ID로 리뷰 목록 조회 (최신순)
     */
    List<Review> findByProductIdOrderByCreatedAtDesc(Long productId);

    /**
     * 상품 ID로 리뷰 목록 조회
     */
    List<Review> findByProductId(Long productId);

    /**
     * 사용자 ID로 리뷰 목록 조회
     */
    List<Review> findByUserId(Long userId);

    /**
     * 사용자로 리뷰 목록 조회
     */
    List<Review> findByUser(User user);
}
