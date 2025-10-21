package com.onandhome.admin.adminOrder.dto;

import com.onandhome.order.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;

/**
 * 주문 항목 (OrderItem) 정보를 담는 DTO
 */
@Getter
@Builder
public class AdminOrderItemDTO {

    private final Long itemId;
    private final Long productId;
    private final String productName;
    private final String productOption;
    private final int unitPrice;
    private final int quantity;
    private final int itemTotalPrice; // 항목별 총액 (단가 * 수량)

    // Entity(OrderItem)를 DTO로 변환하는 정적 팩토리 메서드
    public static AdminOrderItemDTO fromEntity(OrderItem item) {

        // **수정 로직**: OrderItem 엔티티에 getTotalPrice()가 없다면 여기서 계산합니다.
        int totalPrice = item.getUnitPrice() * item.getQuantity();

        return AdminOrderItemDTO.builder()
                .itemId(item.getId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity())
                .itemTotalPrice(totalPrice)
                .build();
    }
}
