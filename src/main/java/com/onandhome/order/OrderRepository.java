package com.onandhome.order;

import com.onandhome.order.entity.Order;
import com.onandhome.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // createdAt 정렬 필드 제거 (Order 엔티티에 없기 때문)
    List<Order> findByUser(User user);

    List<Order> findByUserOrderByCreatedAtDesc(User user);
}