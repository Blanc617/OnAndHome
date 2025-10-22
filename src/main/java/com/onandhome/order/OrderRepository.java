package com.onandhome.order;

import com.onandhome.order.entity.Order;
import com.onandhome.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * 사용자의 모든 주문 조회
     */
    List<Order> findByUser(User user);

    /**
     * 사용자의 주문을 생성 순서(최신순)로 조회
     */
    List<Order> findByUserOrderByCreatedAtDesc(User user);

    /**
     * 주문번호로 주문 조회
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime start, LocalDateTime end);
}
