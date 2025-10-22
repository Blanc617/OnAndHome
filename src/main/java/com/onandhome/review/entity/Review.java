package com.onandhome.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 리뷰 엔티티 (상품명 문자열 저장)
 */
@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자
    private String author;

    // 리뷰 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    // 별점
    private int rating;

    // 상품명 (단순 문자열로 저장)
    private String productName;

    // 작성일
    private LocalDateTime createdAt = LocalDateTime.now();

    /** ✅ 답글 연결 (1:N 관계) */
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewReply> replies = new ArrayList<>();

    /** ✅ 상품명 조회용 메서드 (기존 getProduct 대체) */
    public String getProductNameSafe() {
        return (productName != null && !productName.isEmpty()) ? productName : "(상품명 없음)";
    }
}
