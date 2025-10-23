package com.onandhome.review.dto;

import com.onandhome.review.entity.Review;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/** 리뷰 DTO */
@Getter
@Setter
public class ReviewDTO {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Integer rating;
    private Long productId;
    private Long userId;
    private String author;
    private String productName;
    private LocalDateTime updatedAt;
    private String username;

    /** 엔티티 → DTO 변환 */
    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.rating = review.getRating();
        this.productId = review.getProductId();
        this.userId = review.getUserId();
        this.author = review.getAuthor();
        this.productName = review.getProductName();
        this.updatedAt = review.getUpdatedAt();
        this.username = review.getUsername();
    }
}
