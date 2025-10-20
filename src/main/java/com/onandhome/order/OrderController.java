package com.onandhome.order;

import java.util.List;
import java.util.Map;

import com.onandhome.order.entity.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {
	private final OrderService svc;

	public OrderController(OrderService svc) {
		this.svc = svc;
	}


	@PostMapping("/checkout")
	public Order checkout(@RequestBody Map<String, Object> body) {
		Long userId = Long.valueOf(body.get("userId").toString());
		// 결제 정보(카드 등)는 생략하거나 모의로 처리
		return svc.checkout(userId);
	}

	@GetMapping
	public List<Order> list(@RequestParam Long userId) {
		return svc.getOrders(userId);
	}

	@GetMapping("/{id}")
	public Order get(@PathVariable Long id) {
		return svc.getOrder(id);
	}
}