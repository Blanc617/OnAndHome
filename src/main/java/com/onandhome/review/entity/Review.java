package com.onandhome.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 리뷰 엔티티 (DB 컬럼 완전 매칭 버전)
 */
@Entity
@Getter
@Setter
@Table(name = "review")
public class Review {

    /** ✅ 기본키 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ✅ 리뷰 내용 */
    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    /** ✅ 작성 시각 */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /** ✅ 평점 (예: 1~5점) */
    @Column(name = "rating")
    private Integer rating;

    /** ✅ 상품 ID (외래키로 사용할 수 있음) */
    @Column(name = "product_id")
    private Long productId;

    /** ✅ 작성자 회원 ID */
    @Column(name = "user_id")
    private Long userId;

    /** ✅ 작성자 이름 (별칭 또는 노출명) */
    @Column(name = "author")
    private String author;

    /** ✅ 상품명 */
    @Column(name = "product_name", nullable = false)
    private String productName;

    /** ✅ 수정 시각 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** ✅ 작성자 계정명 */
    @Column(name = "username")
    private String username;

    /** ✅ 답글 목록 (1:N 관계) */
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewReply> replies = new ArrayList<>();

    /** ✅ 수정 시각 자동 업데이트 */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
