package com.onandhome.review;

import com.onandhome.review.dto.ReviewDTO;
import com.onandhome.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/** 리뷰 서비스 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /** ✅ 전체 리뷰 목록 조회 */
    public List<ReviewDTO> findAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
    }

    /** ✅ 개별 리뷰 조회 */
    public ReviewDTO findById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id=" + id));
        return new ReviewDTO(review);
    }
}
