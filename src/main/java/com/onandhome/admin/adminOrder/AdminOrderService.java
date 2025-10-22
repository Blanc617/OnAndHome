package com.onandhome.admin.adminOrder;

import com.onandhome.order.OrderRepository;
import com.onandhome.order.dto.OrderDTO; // ✅ [추가] OrderDTO import
import com.onandhome.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors; // ✅ [추가] Collectors import

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 데이터를 읽기만 하는 서비스라는 뜻
public class AdminOrderService {

    // final로 선언하고 @RequiredArgsConstructor로 자동 주입
    private final OrderRepository orderRepository;

    /**
     * 주문 목록 조회 (날짜 필터링 기능 포함)
     *
     * @param date (LocalDate) - 조회할 날짜 (예: 2025-10-22)
     * @return List<OrderDTO> - ✅ [수정] 반환 타입을 DTO 리스트로 변경
     */
    public List<OrderDTO> getOrders(LocalDate date) { // ✅ [수정] 반환 타입 변경

        List<Order> orders; // DB에서 가져온 엔티티 리스트

        if (date == null) {
            // 1. 날짜가 없으면 (date 파라미터가 안 넘어오면)
            //    -> Repository의 '모든 주문 최신순' 메서드 호출
            orders = orderRepository.findAllByOrderByCreatedAtDesc();
        } else {
            // 2. 날짜가 있으면 (date 파라미터가 넘어오면)
            //    -> '오늘' 날짜의 시작(00:00:00)과 끝(23:59:59) 시간을 계산
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.plusDays(1).atStartOfDay().minusNanos(1);

            //    -> Repository의 '날짜 사이 검색' 메서드 호출
            orders = orderRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(startOfDay, endOfDay);
        }

        // ✅ 3. [핵심] DB 연결(Session)이 끊어지기 전에, Entity List -> DTO List로 변환
        //    (LazyInitializationException 해결)
        List<OrderDTO> orderDTOs = orders.stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());

        // ✅ 4. 변환된 DTO 리스트를 컨트롤러로 반환
        return orderDTOs;
    }
}