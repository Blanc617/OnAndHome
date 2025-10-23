package com.onandhome.review;

import com.onandhome.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 리뷰 레포지토리
 * user 엔티티와의 join 없이 기본 조회만 수행
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // findAllWithUser() 제거 → 기본 findAll() 사용
}
