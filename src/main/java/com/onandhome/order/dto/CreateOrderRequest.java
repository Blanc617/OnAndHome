package com.onandhome.order.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateOrderRequest {
    
    private Long userId;
    
    private List<OrderItemRequest> orderItems;
    
    /**
     * 주문 항목 요청 정보
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class OrderItemRequest {
        private Long productId;
        private int quantity;
    }
}
