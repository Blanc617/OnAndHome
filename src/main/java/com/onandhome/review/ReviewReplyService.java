package com.onandhome.review;

import com.onandhome.review.dto.ReviewReplyDTO;
import com.onandhome.review.entity.Review;
import com.onandhome.review.entity.ReviewReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 리뷰 답글 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewReplyService {

    private final ReviewRepository reviewRepository;
    private final ReviewReplyRepository reviewReplyRepository;

    /** ✅ 특정 리뷰의 답글 전체 조회 */
    public List<ReviewReplyDTO> findByReviewId(Long reviewId) {
        List<ReviewReply> replies = reviewReplyRepository.findAllByReviewIdWithReview(reviewId);
        return replies.stream().map(ReviewReplyDTO::new).collect(Collectors.toList());
    }

    /** ✅ 단일 답글 조회 (수정용) */
    public ReviewReplyDTO findById(Long replyId) {
        ReviewReply reply = reviewReplyRepository.findWithReviewById(replyId);
        return new ReviewReplyDTO(reply);
    }

    /** ✅ 답글 등록 */
    public void createReply(Long reviewId, String content) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다. ID=" + reviewId));

        ReviewReply reply = new ReviewReply();
        reply.setReview(review);
        reply.setUsername("관리자"); // 로그인 연동 시 session 사용자로 교체 가능
        reply.setContent(content);
        reply.setCreatedAt(LocalDateTime.now());

        reviewReplyRepository.save(reply);
    }

    /** ✅ 답글 수정 */
    public void updateReply(Long replyId, String content) {
        ReviewReply reply = reviewReplyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("답글을 찾을 수 없습니다. ID=" + replyId));

        reply.setContent(content);
        reply.setUpdatedAt(LocalDateTime.now());
        reviewReplyRepository.save(reply);
    }

    /** ✅ 답글 삭제 */
    public void deleteReply(Long replyId) {
        reviewReplyRepository.deleteById(replyId);
    }

    /** ✅ 답글이 속한 리뷰 ID 조회 */
    public Long getReviewIdByReplyId(Long replyId) {
        ReviewReply reply = reviewReplyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("답글을 찾을 수 없습니다. ID=" + replyId));
        return reply.getReview().getId();
    }
}
