package com.onandhome.review;

import com.onandhome.review.dto.ReviewDTO;
import com.onandhome.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepo;

    /** ✅ 전체 리뷰 조회 (DTO 변환) */
    @Transactional(readOnly = true)
    public List<ReviewDTO> getAllReviews() {
        return reviewRepo.findAll().stream()
                .map(ReviewDTO::new) // 엔티티 → DTO 변환
                .toList(); // Java 17 이상, 11이면 Collectors.toList()로 변경
    }

    /** ✅ 리뷰 단건 조회 (DTO 변환) */
    @Transactional(readOnly = true)
    public ReviewDTO getReview(Long id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // LazyInitialization 방지용 접근
        if (review.getProductName() == null) {
            review.setProductName("상품명 없음");
        }

        return new ReviewDTO(review);
    }

    /** ✅ 엔티티 그대로 반환 (수정 등 내부 로직용) */
    @Transactional(readOnly = true)
    public Review getReviewEntity(Long id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // replies, productName 등 접근 시 Lazy 에러 방지
        review.getReplies().size();
        review.getProductName(); // 단순 문자열 접근이지만 명시적으로 초기화

        return review;
    }

    /** ✅ 리뷰 저장 */
    public Review save(Review review) {
        return reviewRepo.save(review);
    }

    /** ✅ 리뷰 삭제 */
    public void deleteReview(Long id) {
        reviewRepo.deleteById(id);
    }

    /** ✅ findAll() : 컨트롤러용 */
    @Transactional(readOnly = true)
    public List<Review> findAll() {
        List<Review> list = reviewRepo.findAll();
        // Lazy 초기화 (replies 접근 시 오류 방지)
        list.forEach(r -> {
            r.getReplies().size();
            r.getProductName();
        });
        return list;
    }

    /** ✅ findById() : 컨트롤러용 */
    @Transactional(readOnly = true)
    public Review findById(Long id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // LazyInitialization 방지
        review.getReplies().size();
        review.getProductName();
        return review;
    }
}
