package com.onandhome.admin.adminOrder.entity;

import java.time.LocalDateTime;
import java.util.List;

// ❌ AdminOrder 엔티티에서 DTO(AdminOrderItemDTO)를 import 할 필요가 없습니다. (제거)
import com.onandhome.order.entity.OrderItem; // ✅ 실제 주문 항목 엔티티를 import 해야 합니다.
import com.onandhome.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class AdminOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
    private double totalAmount;

    // ✅ 추가된 배송 및 결제 정보
    private double shippingFee;
    private String paymentMethod;
    private String recipientName;
    private String recipientPhone;
    private String shippingAddress;

    // ✅ 회원 정보 (ManyToOne 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // ✅ 주문 항목 목록 (OneToMany 관계)
    // DTO 대신 실제 엔티티인 OrderItem을 사용해야 합니다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items; // 🚨 AdminOrderItemDTO -> OrderItem으로 변경

    // 초기 생성 시각 및 상태 설정
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PENDING";
        }
    }
}
