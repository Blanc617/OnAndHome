package com.onandhome.admin.adminOrder;

import com.onandhome.admin.adminOrder.dto.AdminOrderResponseDTO;
import com.onandhome.admin.adminOrder.dto.OrderStatusUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 관리자용 주문 관리를 위한 REST API Controller
 * 기본 경로: /api/admin/orders
 */
@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    /**
     * 1. 주문 목록 조회 (list) 및 검색 (search)
     * GET /api/admin/orders?status=PAID&searchKeyword=주문번호
     */
    @GetMapping
    public ResponseEntity<List<AdminOrderResponseDTO>> listOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String searchKeyword) {

        List<AdminOrderResponseDTO> orders;

        if (status != null || searchKeyword != null) {
            // 4. search 기능: 검색 및 필터링
            orders = adminOrderService.searchOrders(status, searchKeyword);
        } else {
            // 1. list 기능: 전체 목록 조회
            orders = adminOrderService.findAllOrders();
        }

        // HTTP 200 OK와 함께 데이터 반환
        return ResponseEntity.ok(orders);
    }

    /**
     * 2. 특정 주문 상세 조회 (detail)
     * GET /api/admin/orders/{orderId}
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<AdminOrderResponseDTO> getOrderDetail(@PathVariable Long orderId) {
        // ResourceNotFoundException은 GlobalExceptionHandler에서 처리됩니다.
        AdminOrderResponseDTO orderDetails = adminOrderService.findOrderDetails(orderId);
        return ResponseEntity.ok(orderDetails);
    }

    /**
     * 3. 주문 상태 업데이트 (updateStatus)
     * PATCH /api/admin/orders/{orderId}/status
     */
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequest request) {

        adminOrderService.updateOrderStatus(orderId, request.getNewStatus());

        // HTTP 204 No Content 반환
        return ResponseEntity.noContent().build();
    }
}