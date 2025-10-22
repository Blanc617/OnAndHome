package com.onandhome.review.dto;

import com.onandhome.review.entity.Review;
import com.onandhome.review.entity.ReviewReply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewReplyDTO {

    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    // ✅ 엔티티 → DTO 변환
    public ReviewReplyDTO(ReviewReply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.createdAt = reply.getCreatedAt();
        this.username = (reply.getUser() != null)
                ? reply.getUser().getUsername() // ✅ User.username 필드 접근
                : (reply.getAuthor() != null ? reply.getAuthor() : "관리자");
    }

    // ✅ DTO → 엔티티 변환
    public ReviewReply toEntity(Review review) {
        ReviewReply reply = new ReviewReply();
        reply.setReview(review);
        reply.setContent(this.content);
        reply.setCreatedAt(LocalDateTime.now());
        reply.setUser(null); // ✅ 나중에 로그인 유저 연결 시 주입
        reply.setAuthor(this.username);
        return reply;
    }
}
