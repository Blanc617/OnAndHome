package com.onandhome.review;

import com.onandhome.review.dto.ReviewReplyDTO;
import com.onandhome.review.entity.Review;
import com.onandhome.review.entity.ReviewReply;
import com.onandhome.review.ReviewRepository;
import com.onandhome.review.ReviewReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ✅ 리뷰 답글 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewReplyService {

    private final ReviewReplyRepository reviewReplyRepository;
    private final ReviewRepository reviewRepository;

    /** ✅ 리뷰 ID 기준으로 답글 조회 (DTO 변환 포함) */
    @Transactional(readOnly = true)
    public List<ReviewReplyDTO> findByReviewId(Long reviewId) {
        return reviewReplyRepository.findByReviewId(reviewId)
                .stream()
                .map(ReviewReplyDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /** ✅ 답글 단일 조회 */
    @Transactional(readOnly = true)
    public ReviewReplyDTO findById(Long replyId) {
        ReviewReply reply = reviewReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다. ID=" + replyId));
        return ReviewReplyDTO.fromEntity(reply);
    }

    /** ✅ 답글 생성 (관리자 / 사용자 공통) */
    public void createReply(Long reviewId, String content, String author, String username, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. ID=" + reviewId));

        ReviewReply reply = new ReviewReply();
        reply.setReview(review);
        reply.setContent(content);
        reply.setAuthor(author);
        reply.setUsername(username);

        // ✅ 관리자 계정이면 userId를 null 처리 (외래키 제약 회피)
        if (userId != null && userId > 0) {
            reply.setUserId(userId);
        } else {
            reply.setUserId(null);
        }

        reply.setCreatedAt(LocalDateTime.now());
        reply.setUpdatedAt(LocalDateTime.now());

        reviewReplyRepository.save(reply);
    }

    /** ✅ 답글 수정 */
    public void updateReply(Long replyId, String content) {
        ReviewReply reply = reviewReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다. ID=" + replyId));

        reply.setContent(content);
        reply.setUpdatedAt(LocalDateTime.now());
        reviewReplyRepository.save(reply);
    }

    /** ✅ 답글 삭제 */
    public void deleteReply(Long replyId) {
        ReviewReply reply = reviewReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다. ID=" + replyId));

        reviewReplyRepository.delete(reply);
    }
}
