package com.onandhome.order;

import java.util.List;


import com.onandhome.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUser(User user);


    Order save(Order order);

    List<Order> findByUser(com.onandhome.user.User user);
}