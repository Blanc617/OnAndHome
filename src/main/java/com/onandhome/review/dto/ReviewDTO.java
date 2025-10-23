package com.onandhome.review.dto;

import com.onandhome.review.entity.Review;
import com.onandhome.review.dto.ReviewReplyDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ✅ 리뷰 DTO
 * Review → ReviewDTO 변환 시, ReviewReply까지 포함
 */
@Getter
@Setter
public class ReviewDTO {

    private Long id;                // 리뷰 ID
    private String content;         // 리뷰 내용
    private int rating;             // 평점
    private String productName;     // 상품명
    private String author;          // 작성자명
    private String username;        // 사용자 계정명
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ✅ 리뷰에 연결된 답글 목록
    private List<ReviewReplyDTO> replies;

    /** ✅ 엔티티 → DTO 변환 */
    public static ReviewDTO fromEntity(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setRating(review.getRating());
        dto.setProductName(review.getProductName());
        dto.setAuthor(review.getAuthor());
        dto.setUsername(review.getUsername());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdatedAt());

        // ✅ LazyInitializationException 방지를 위한 null-safe 변환
        if (review.getReplies() != null) {
            dto.setReplies(
                    review.getReplies().stream()
                            .map(ReviewReplyDTO::fromEntity)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
