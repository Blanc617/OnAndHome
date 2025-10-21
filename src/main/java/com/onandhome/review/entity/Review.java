package com.onandhome.review.entity;

import com.onandhome.user.entity.User;
import com.onandhome.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String content;

    private int rating; // 별점 (1~5)

    private LocalDateTime createdAt = LocalDateTime.now();
}
