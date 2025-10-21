package com.onandhome.product.entity;

import com.onandhome.qna.entity.Qna;
import com.onandhome.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //실수로 비어있는 객체 생성 막아 안전성 높이기
@Table(name = "product")
@ToString(exclude = {"reviews", "qnas"})
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
	private Long id;

    @Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "description", length = 1000)
	private String description;

    @Column(name = "price", nullable = false)
	private int price; // 원 단위로 사용

    @Column(name = "stock", nullable = false)
	private int stock;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at = LocalDateTime.now();

 /*   @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Qna> qnas = new ArrayList<>();*/

    //정적 팩토리 메소드 추가
    public static Product createProduct(String name, String description, int price, int stock, String category, String status) {
        Product product = new Product();
        product.name = name;
        product.price = price;
        product.description = description;
        product.stock = stock;
        product.category = category;
        product.status = status;
        product.created_at = LocalDateTime.now();
        product.updated_at = LocalDateTime.now();
        return product;
    }
    //비즈니스 로직
    public void addStock(int quantity) {
        this.stock += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }
}
