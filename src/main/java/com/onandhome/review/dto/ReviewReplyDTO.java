package com.onandhome.review.dto;

import com.onandhome.review.entity.ReviewReply;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/** 리뷰 답글 DTO */
@Getter
@Setter
public class ReviewReplyDTO {

    private Long id;
    private Long reviewId;
    private String content;
    private String username;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String reviewProductName;

    public ReviewReplyDTO(ReviewReply reply) {
        this.id = reply.getId();
        this.reviewId = reply.getReview() != null ? reply.getReview().getId() : null;
        this.content = reply.getContent();
        this.username = reply.getUsername();
        this.author = reply.getAuthor();
        this.createdAt = reply.getCreatedAt();
        this.updatedAt = reply.getUpdatedAt();
        this.reviewProductName = reply.getReview() != null ? reply.getReview().getProductName() : null;
    }
}
