package com.onandhome.review.dto;

import com.onandhome.review.entity.Review;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReviewDTO {

    private Long id;
    private String author;       // ✅ 필드 이름 변경
    private String productName;
    private String content;
    private LocalDateTime createdAt;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.author = review.getAuthor();         // ✅ author 필드로 매핑
        this.productName = review.getProductName();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}
