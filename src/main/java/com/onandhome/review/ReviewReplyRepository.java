package com.onandhome.review;

import com.onandhome.review.entity.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * 리뷰 답글 리포지토리
 */
public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {

    /** ✅ review까지 함께 가져오기 (LazyInitializationException 방지) */
    @Query("SELECT rr FROM ReviewReply rr JOIN FETCH rr.review WHERE rr.id = :id")
    ReviewReply findWithReviewById(Long id);

    /** ✅ 특정 리뷰 ID 기준으로 reply + review 같이 가져오기 */
    @Query("SELECT rr FROM ReviewReply rr JOIN FETCH rr.review WHERE rr.review.id = :reviewId")
    List<ReviewReply> findAllByReviewIdWithReview(Long reviewId);

    /** ✅ 단순 조회 (JOIN 없이 사용 가능) */
    List<ReviewReply> findByReviewId(Long reviewId);
}
