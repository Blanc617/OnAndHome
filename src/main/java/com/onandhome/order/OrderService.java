package com.onandhome.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.onandhome.cart.entity.CartItem;
import com.onandhome.cart.CartItemRepository;
import com.onandhome.order.entity.Order;
import com.onandhome.order.entity.OrderItem;
import com.onandhome.product.ProductRepository;
import com.onandhome.user.entity.User;
import com.onandhome.user.UserRepository;
import org.springframework.stereotype.Service;



@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public OrderService(OrderRepository orderRepository, CartItemRepository cartItemRepository, UserRepository userRepository,
			ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.cartItemRepository = cartItemRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	// 간단한 checkout: cart -> order, 결제 시 PAID로 변경
	public Order checkout(Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		if (cartItems.isEmpty())
			throw new RuntimeException("Cart empty");

		List<OrderItem> items = cartItems.stream().map(ci -> {
			OrderItem oi = new OrderItem();
			oi.setProductId(ci.getProduct().getId());
			oi.setProductName(ci.getProduct().getName());
			oi.setUnitPrice(ci.getProduct().getPrice());
			oi.setQuantity(ci.getQuantity());
			return oi;
		}).collect(Collectors.toList());

		int total = items.stream().mapToInt(i -> i.getUnitPrice() * i.getQuantity()).sum();

		Order order = new Order();
		order.setUser(user);
		order.setCreatedAt(LocalDateTime.now());
		order.setStatus("PAID"); // 결제는 모의로 바로 완료 처리
		order.setTotalPrice(total);
		order.setItems(items);

		Order saved = orderRepository.save(order);
        cartItemRepository.deleteByUser(user); // 장바구니 비우기
		return saved;
	}

	public List<Order> getOrders(Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		return orderRepository.findByUser(user);
	}

	public Order getOrder(Long orderId) {
		return (Order) orderRepository.findById(orderId).orElse(null);
	}
}