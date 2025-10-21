package com.onandhome.admin.adminOrder.dto;

import com.onandhome.admin.adminOrder.entity.AdminOrder;
// OrderItem DTO는 같은 패키지에 있으므로 명시적 import가 필수는 아닐 수 있지만,
// 명확성을 위해 포함합니다. (IntelliJ가 자동으로 처리)

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자 주문 상세 조회 및 목록 조회 응답 DTO
 */
@Getter
@Builder
public class AdminOrderResponseDTO {

    private final Long orderId;
    private final String orderNumber;
    private final Long userId;
    private final String userName; // 회원 이름
    private final LocalDateTime createdAt;
    private final LocalDateTime paidAt;
    private final String status;
    private final double totalAmount;

    // ✅ 핵심 수정 부분: AdminOrderItemDTO 리스트를 필드로 포함
    private final List<AdminOrderItemDTO> items;

    // 배송 정보
    private final double shippingFee;
    private final String recipientName;
    private final String recipientPhone;
    private final String shippingAddress;

    // 결제 정보
    private final String paymentMethod;


    // Entity(AdminOrder)를 DTO로 변환하는 정적 팩토리 메서드
    public static AdminOrderResponseDTO fromEntity(AdminOrder order) {

        // OrderItem 엔티티 리스트를 AdminOrderItemDTO 리스트로 변환
        List<AdminOrderItemDTO> itemDtos = order.getItems().stream()
                .map(AdminOrderItemDTO::fromEntity)
                .collect(Collectors.toList());

        return AdminOrderResponseDTO.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .userName(order.getUser() != null ? order.getUser().getUsername() : "탈퇴 회원")
                .createdAt(order.getCreatedAt())
                .paidAt(order.getPaidAt())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .shippingFee(order.getShippingFee())
                .recipientName(order.getRecipientName())
                .recipientPhone(order.getRecipientPhone())
                .shippingAddress(order.getShippingAddress())
                .paymentMethod(order.getPaymentMethod())
                .items(itemDtos) // 변환된 항목 DTO 리스트 설정
                .build();
    }
}
