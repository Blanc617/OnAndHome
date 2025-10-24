package com.onandhome.admin.adminProduct.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_code", unique = true, length = 50)
	private String productCode; // 상품코드 (PC-001, PC-002 등)

	@Column(nullable = false)
	private String name;

	@Column(length = 1000)
	private String description;

	@Column(nullable = false)
	private int price; // 정상가 (원 단위)

	@Column(name = "sale_price")
	private Integer salePrice; // 할인가 (원 단위)

	@Column(nullable = false)
	private int stock; // 재고수량

	private String thumbnailImage; // 썸네일 이미지 URL (컬럼명: thumbnail_image)

	private String detailImage; // 상품 상세 이미지 URL (컬럼명: detail_image)

	@Column(name = "category")
	private String category; // 소 카테고리 (TV, 오디오, 냉장고 등)

	@Column(name = "manufacturer")
	private String manufacturer; // 제조사

	@Column(name = "country")
	private String country; // 제조국

	@Column(name = "created_at")
	private LocalDateTime createdAt; // 등록일자

	@Column(name = "updated_at")
	private LocalDateTime updatedAt; // 수정일자

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 재고 차감 (주문 시)
	 */
	public void removeStock(int quantity) {
		if (this.stock < quantity) {
			throw new IllegalArgumentException("재고가 부족합니다. 현재 재고: " + this.stock);
		}
		this.stock -= quantity;
	}

	/**
	 * 재고 증가 (주문 취소 시)
	 */
	public void addStock(int quantity) {
		this.stock += quantity;
	}
}
