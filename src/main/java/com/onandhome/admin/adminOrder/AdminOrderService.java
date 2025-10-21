package com.onandhome.admin.adminOrder;

import com.onandhome.admin.adminOrder.dto.AdminOrderResponseDTO;
import com.onandhome.admin.adminOrder.entity.AdminOrder;
// ⬇️ 이전: import com.onandhome.admin.adminOrder.exception.ResourceNotFoundException;
// ⬇️ 수정: 예외 클래스의 실제 위치(com.onandhome.exception)로 경로 변경
import com.onandhome.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자용 주문 관리 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminOrderService {

    private final AdminOrderRepository adminOrderRepository;

    /**
     * 1. 주문 목록 조회 (list)
     */
    public List<AdminOrderResponseDTO> findAllOrders() {
        List<AdminOrder> orders = adminOrderRepository.findAllByOrderByCreatedAtDesc();
        return orders.stream()
                .map(AdminOrderResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /*** 2. 특정 주문 ID의 상세 정보를 조회 (detail)
     * @throws ResourceNotFoundException 해당 ID의 주문이 없을 경우 발생
     */
    public AdminOrderResponseDTO findOrderDetails(Long orderId) {

        AdminOrder order = adminOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        // N+1 문제 방지를 위한 OrderItem 강제 초기화
        if (order.getItems() != null) {
            order.getItems().size();
        }

        return AdminOrderResponseDTO.fromEntity(order);
    }

    /*** 3. 주문 상태 변경 (updateStatus)
     */
    @Transactional
    public void updateOrderStatus(Long orderId, String newStatus) {
        AdminOrder order = adminOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        // 상태 유효성 검사 등 비즈니스 로직 추가 필요
        order.setStatus(newStatus);
    }

    /*** 4. 주문 검색 및 필터링 (search)
     */
    public List<AdminOrderResponseDTO> searchOrders(String status, String searchKeyword) {
        // TODO: 실제 동적 쿼리 구현 필요
        List<AdminOrder> orders = adminOrderRepository.findAll();
        return orders.stream()
                .map(AdminOrderResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
