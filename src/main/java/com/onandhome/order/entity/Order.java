package com.onandhome.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.onandhome.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

// @Setter를 사용하면 모든 필드에 Setter가 자동 생성됩니다.
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber; // 주문 번호

    private String status; // 주문 상태 (예: PENDING, PAID, SHIPPED, DELIVERED)

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime paidAt; // 결제 완료 시각

    private double totalAmount; // 총 결제 금액

    // ✅ 회원 정보 (ManyToOne 관계)
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
    @JoinColumn(name = "user_id")
    private User user;

    // ✅ 주문 항목 (OneToMany 관계) - DTO가 아닌 OrderItem 엔티티를 사용해야 합니다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items; // 이 필드의 타입은 List<OrderItem> 입니다.

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

    // ⭐ 중요: 기존 코드에서 불필요하게 수동으로 작성되었던 Setter 및 Getter들은
    // Lombok(@Getter, @Setter)이 자동으로 생성하므로 모두 제거했습니다.
    // 특히 DTO를 인자로 받던 public void setItems(...) 메서드는 JPA 오류의 원인이므로 반드시 제거해야 합니다.
}
