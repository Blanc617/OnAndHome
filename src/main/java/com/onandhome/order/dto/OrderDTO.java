package com.onandhome.order.dto;

import com.onandhome.order.entity.Order;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderDTO {

    private Long orderId;

    private String orderNumber;

    private Long userId;

    private String status; // OrderStatus enum을 String으로

    private int totalPrice;

    private List<OrderItemDTO> orderItems;

    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    /**
     * Order Entity를 DTO로 변환
     */
    public static OrderDTO fromEntity(Order order) {
        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                .map(OrderItemDTO::fromEntity)
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUser().getId())
                .status(order.getStatus().toString())
                .totalPrice(order.getTotalPrice())
                .orderItems(orderItemDTOs)
                .createdAt(order.getCreatedAt())
                .paidAt(order.getPaidAt())
                .build();
    }
}
