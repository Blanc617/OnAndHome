package com.onandhome.review.entity;

import com.onandhome.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 리뷰 답글 엔티티
 */
@Entity
@Getter
@Setter
public class ReviewReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ✅ 답글 내용 */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /** ✅ 작성자명 (닉네임 또는 관리자명 등) */
    private String author;

    /** ✅ 작성일시 */
    private LocalDateTime createdAt = LocalDateTime.now();

    /** ✅ 연결된 리뷰 (N:1 관계) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    /** ✅ 연결된 유저 (N:1 관계, 로그인 유저 정보) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
