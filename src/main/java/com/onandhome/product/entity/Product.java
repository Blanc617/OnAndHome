package com.onandhome.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private int price; // 정상가 (원 단위)

    @Column(nullable = false)
    private int stock;

    private String thumbnailImage; // 썸네일 이미지 URL (컬럼명: thumbnail_image)

    private String detailImage; // 상품 상세 이미지 URL (컬럼명: detail_image)
}
