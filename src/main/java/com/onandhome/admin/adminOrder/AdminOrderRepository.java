package com.onandhome.admin.adminOrder;

import com.onandhome.admin.adminOrder.entity.AdminOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 관리자용 주문 데이터베이스 접근 계층 (Repository)
 * AdminOrder 엔티티와 Long 타입 ID를 사용합니다.
 */
@Repository
public interface AdminOrderRepository extends JpaRepository<AdminOrder, Long> {

    /**
     * 모든 주문 목록을 주문 생성일(createdAt) 기준 내림차순(최신순)으로 조회합니다.
     * 관리자 주문 목록 페이지에서 사용됩니다.
     */
    List<AdminOrder> findAllByOrderByCreatedAtDesc();

    /**
     * 주문 ID를 통해 단일 주문을 조회합니다.
     * OrderItem 정보도 함께 로드하기 위해 Fetch Join을 사용할 수 있지만,
     * 여기서는 Optional<T> 기본 기능을 사용하고 Service 계층에서 FetchType.LAZY 처리를 위임합니다.
     */
    Optional<AdminOrder> findById(Long id);

    // TODO: 필요 시 특정 조건(예: 상태, 기간)에 따른 주문 검색 메서드를 추가합니다.
}