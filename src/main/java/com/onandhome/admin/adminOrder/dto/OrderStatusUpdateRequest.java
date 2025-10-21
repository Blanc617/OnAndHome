package com.onandhome.admin.adminOrder.dto;

// ✅ Lombok import 추가 (IDE가 자동으로 추가해주지 않는 경우를 대비)
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 주문 상태 변경 요청을 위한 DTO
 */
@Getter
@NoArgsConstructor // Lombok 어노테이션 사용
@AllArgsConstructor // Lombok 어노테이션 사용
public class OrderStatusUpdateRequest {

    // 새로운 주문 상태
    private String newStatus;
}
