package com.onandhome.cart;

import java.util.List;

import com.onandhome.cart.entity.CartItem;
import com.onandhome.user.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByUser(User user);

	void deleteByUser(User user);

}
