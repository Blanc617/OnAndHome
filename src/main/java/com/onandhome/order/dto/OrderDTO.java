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
    private String userIdStr;
    private String username;
    private String status;
    private int totalPrice;
    private List<OrderItemDTO> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    public static OrderDTO fromEntity(Order order) {
        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                .map(OrderItemDTO::fromEntity)
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUser().getId())
                .userIdStr(order.getUser().getUserId())
                .username(order.getUser().getUsername())
                .status(order.getStatus().toString())
                .totalPrice(order.getTotalPrice())
                .orderItems(orderItemDTOs)
                .createdAt(order.getCreatedAt())
                .paidAt(order.getPaidAt())
                .build();
    }
}
