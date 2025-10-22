package com.onandhome.review;

import com.onandhome.review.entity.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {
    List<ReviewReply> findByReviewId(Long reviewId);
}
