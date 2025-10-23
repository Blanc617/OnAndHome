package com.onandhome.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 리뷰 답글 엔티티 (DB 컬럼 완전 매칭 버전)
 */
@Entity
@Getter
@Setter
@Table(name = "review_reply")
public class ReviewReply {

    /** ✅ 기본키 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ✅ 답글 내용 */
    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    /** ✅ 작성 시각 */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /** ✅ 부모 리뷰 (외래키) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    /** ✅ 사용자 ID (회원 고유 식별자, 선택적으로 사용 가능) */
    @Column(name = "user_id")
    private Long userId;

    /** ✅ 작성자 이름 (별칭, 표시용) */
    @Column(name = "author")
    private String author;

    /** ✅ 수정 시각 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** ✅ 작성자 계정명 (로그인 사용자명) */
    @Column(name = "username", nullable = false)
    private String username;

    /** ✅ 수정 시각 자동 업데이트 */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
