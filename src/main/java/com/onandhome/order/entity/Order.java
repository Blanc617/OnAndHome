package com.onandhome.order.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.onandhome.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    // ✅ 회원 정보 추가 (ManyToOne 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private String orderNumber;

    private LocalDateTime paidAt; //결제 시간

    public enum OrderStatus {
        ORDERED, CANCELED, DELIVERING, DELIVERED
    }

    //생성 메소드
    public static Order create(User user, List<OrderItem> orderItems) {
        Order order = new Order();
        order.user = user;
        order.createdAt = LocalDateTime.now(); // 생성 시간 설정
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.status = OrderStatus.ORDERED; // 생성 즉시 '주문 완료' 상태
        order.orderNumber = UUID.randomUUID().toString().substring(0, 12);
        order.calculateTotalPrice();
        return order;
    }

    //연관관계 편의 메소드
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //비즈니스 로직

    /**
     * 주문 결제 처리
     */
    public void pay() {
        //결제시 나중에 이미 취소된 주문을 결제하거나 중복 결제하는 등의 문제가 생길 수 있어 조건문 필요
        this.status = OrderStatus.ORDERED;
        this.calculateTotalPrice();
        this.paidAt = LocalDateTime.now();
    }

    /**
     * 주문 취소
     */
    public void cancel() {
        if (status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.status = OrderStatus.CANCELED;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 총 가격 계산
     */
    private void calculateTotalPrice() {
        this.totalPrice = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
