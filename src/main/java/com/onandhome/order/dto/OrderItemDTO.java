package com.onandhome.order.dto;

import com.onandhome.order.entity.OrderItem;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderItemDTO {
    
    private Long orderItemId;
    
    private Long productId;
    
    private String productName;
    
    private int orderPrice;
    
    private int quantity;
    
    /**
     * OrderItem Entity를 DTO로 변환
     */
    public static OrderItemDTO fromEntity(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .orderItemId(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .orderPrice(orderItem.getOrderPrice())
                .quantity(orderItem.getCount())
                .build();
    }
    
    /**
     * 주문 상품의 총 가격 계산
     */
    public int getTotalPrice() {
        return orderPrice * quantity;
    }
}
