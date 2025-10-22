package com.onandhome.review;

import com.onandhome.review.entity.Review;
import com.onandhome.review.entity.ReviewReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewReplyService {

    private final ReviewReplyRepository replyRepository;
    private final ReviewRepository reviewRepository;

    /** ✅ 답글 등록 */
    public void createReply(Long reviewId, String content, String author) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
        ReviewReply reply = new ReviewReply();
        reply.setReview(review);
        reply.setContent(content);
        reply.setAuthor(author);
        replyRepository.save(reply);
    }

    /** ✅ 답글 수정 */
    public void updateReply(Long id, String content) {
        ReviewReply reply = replyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("답글이 존재하지 않습니다."));
        reply.setContent(content);
        replyRepository.save(reply);
    }

    /** ✅ 답글 목록 조회 */
    @Transactional(readOnly = true)
    public List<ReviewReply> findByReviewId(Long reviewId) {
        return replyRepository.findByReviewId(reviewId);
    }

    /** ✅ 답글 단건 조회 (Lazy 초기화 포함) */
    @Transactional(readOnly = true)
    public ReviewReply findById(Long id) {
        ReviewReply reply = replyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("답글이 존재하지 않습니다."));
        if (reply.getReview() != null) {
            reply.getReview().getProductName(); // Lazy 초기화
        }
        return reply;
    }

    /** ✅ 답글 삭제 */
    public void deleteReply(Long id) {
        replyRepository.deleteById(id);
    }
}
