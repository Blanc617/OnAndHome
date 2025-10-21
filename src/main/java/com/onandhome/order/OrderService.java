package com.onandhome.order;

import com.onandhome.cart.CartItemRepository;
import com.onandhome.cart.entity.CartItem;
import com.onandhome.order.entity.Order;
import com.onandhome.order.entity.OrderItem;
import com.onandhome.user.UserRepository;
import com.onandhome.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final CartItemRepository cartRepo;

    @Transactional(readOnly = true)
    public List<Order> list(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return orderRepo.findByUserOrderByCreatedAtDesc(user);
    }

    @Transactional(readOnly = true)
    public List<CartItem> checkout(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return cartRepo.findByUser(user);
    }

    @Transactional
    public Order create(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        List<CartItem> cartItems = cartRepo.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        Order order = new Order();
        order.setUser(user);
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderNumber(UUID.randomUUID().toString().substring(0, 12));
        List<OrderItem> items = new ArrayList<>();
        double total = 0.0;
        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setPrice(ci.getProduct().getPrice());
            oi.setQuantity(ci.getQuantity());
            items.add(oi);
            total += ci.getProduct().getPrice() * ci.getQuantity();
        }
        order.setItems(items);
        order.setTotalAmount(total);
        Order saved = orderRepo.save(order);
        cartRepo.deleteByUser(user);
        return saved;
    }

    @Transactional
    public Order pay(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        order.setStatus("PAID");
        order.setPaidAt(LocalDateTime.now());
        return orderRepo.save(order);
    }

    @Transactional(readOnly = true)
    public Order detail(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow();
    }

    @Transactional(readOnly = true)
    public String track(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        return switch (order.getStatus()) {
            case "CREATED" -> "PREPARING";
            case "PAID" -> "IN_TRANSIT";
            case "DELIVERED" -> "DELIVERED";
            default -> "UNKNOWN";
        };
    }
}
