package com.onandhome.cart;

import java.util.List;
import java.util.Map;

import com.onandhome.cart.entity.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	private final CartService cartService;


	// userId는 간단히 쿼리로 넘깁니다 (실제 서비스면 인증 필요)
	@GetMapping
	public List<CartItem> getCart(@RequestParam Long userId) {
		return cartService.getCartItems(userId);
	}

	@PostMapping("/add")
	public CartItem add(@RequestBody Map<String, Object> body) {
		Long userId = Long.valueOf(body.get("userId").toString());
		Long productId = Long.valueOf(body.get("productId").toString());
		int qty = Integer.parseInt(body.get("quantity").toString());
		return cartService.addToCart(userId, productId, qty);
	}

	@PostMapping("/clear")
	public String clear(@RequestBody Map<String, Object> body) {
		Long userId = Long.valueOf(body.get("userId").toString());
        cartService.clearCart(userId);
		return "OK";
	}
}
