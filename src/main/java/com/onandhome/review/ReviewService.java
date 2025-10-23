package com.onandhome.review;

import com.onandhome.review.dto.ReviewDTO;
import com.onandhome.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ✅ 리뷰 서비스
 * - DB의 작성자(author), 상품명(productName), 날짜(createdAt) 모두 강제 초기화
 * - Lazy 로딩 및 캐시 문제 방지
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /** ✅ 전체 리뷰 목록 조회 */
    @Transactional(readOnly = true)
    public List<ReviewDTO> findAll() {
        List<Review> reviews = reviewRepository.findAll();

        // ✅ 캐시 초기화 (DB 최신 데이터 반영)
        reviews.forEach(r -> {
            r.getAuthor();        // 작성자명
            r.getProductName();   // 상품명
            r.getContent();       // 내용
            r.getCreatedAt();     // 작성일
        });

        return reviews.stream()
                .map(ReviewDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /** ✅ 개별 리뷰 조회 */
    @Transactional(readOnly = true)
    public ReviewDTO findById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id=" + id));

        // ✅ Lazy 초기화
        review.getAuthor();
        review.getProductName();
        review.getContent();
        review.getCreatedAt();

        return ReviewDTO.fromEntity(review);
    }

    /** ✅ 리뷰 삭제 */
    @Transactional
    public void deleteById(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("리뷰가 존재하지 않습니다. id=" + id);
        }
        reviewRepository.deleteById(id);
    }
}
