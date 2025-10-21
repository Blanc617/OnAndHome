package com.onandhome.cart;

import com.onandhome.cart.entity.CartItem;
import com.onandhome.product.entity.Product;
import com.onandhome.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    void deleteByUser(User user);
    Optional<CartItem> findByUserAndProduct(User user, Product product);
}
