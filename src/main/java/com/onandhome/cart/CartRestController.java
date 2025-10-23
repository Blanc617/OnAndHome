package com.onandhome.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onandhome.cart.entity.CartItem;
import com.onandhome.user.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 장바구니 REST API 컨트롤러
 * 장바구니 담기, 조회, 수정, 삭제 기능 제공
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartRestController {

    private final CartService cartService;
    private static final String SESSION_USER_KEY = "loginUser";

    /**
     * 장바구니 담기
     * POST /api/cart/add
     * 
     * 요청 body:
     * {
     *     "productId": 1,
     *     "quantity": 1
     * }
     */
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestBody AddToCartRequest request,
            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 세션에서 사용자 정보 조회
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);
            
            if (loginUser == null) {
                log.warn("로그인하지 않은 사용자의 장바구니 담기 시도");
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            log.info("장바구니 담기 요청 - 사용자: {}, 상품 ID: {}, 수량: {}", 
                    loginUser.getId(), request.getProductId(), request.getQuantity());

            // 입력값 검증
            if (request.getProductId() == null || request.getProductId() <= 0) {
                response.put("success", false);
                response.put("message", "올바른 상품 ID를 입력하세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (request.getQuantity() <= 0) {
                response.put("success", false);
                response.put("message", "수량은 1 이상이어야 합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            CartItem cartItem = cartService.addToCart(
                    loginUser.getId(), 
                    request.getProductId(),
                    request.getQuantity()
            );

            response.put("success", true);
            response.put("message", "장바구니에 상품이 추가되었습니다.");
            response.put("data", cartItem);
            log.info("장바구니 담기 성공 - 장바구니 ID: {}", cartItem.getId());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("입력 값 오류: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error("장바구니 담기 중 오류: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "장바구니에 상품을 추가하는 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 사용자의 장바구니 조회
     * GET /api/cart
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCart(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 세션에서 사용자 정보 조회
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);
            
            if (loginUser == null) {
                log.warn("로그인하지 않은 사용자의 장바구니 조회 시도");
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            log.info("장바구니 조회 요청 - 사용자: {}", loginUser.getId());

            List<CartItem> cartItems = cartService.getCartItems(loginUser.getId());

            response.put("success", true);
            response.put("data", cartItems);
            response.put("count", cartItems.size());
            log.info("장바구니 조회 성공 - 아이템 개수: {}", cartItems.size());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("장바구니 조회 중 오류: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "장바구니를 조회하는 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 장바구니 아이템 수량 수정
     * PUT /api/cart/{cartItemId}
     * 
     * 요청 body:
     * {
     *     "quantity": 5
     * }
     */
    @PutMapping("/{cartItemId}")
    public ResponseEntity<Map<String, Object>> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestBody UpdateQuantityRequest request,
            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 세션에서 사용자 정보 조회
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);
            
            if (loginUser == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            if (request.getQuantity() <= 0) {
                response.put("success", false);
                response.put("message", "수량은 1 이상이어야 합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            log.info("장바구니 수량 수정 요청 - 사용자: {}, 아이템 ID: {}, 새 수량: {}", 
                    loginUser.getId(), cartItemId, request.getQuantity());

            CartItem updatedItem = cartService.updateQuantity(cartItemId, request.getQuantity());

            response.put("success", true);
            response.put("message", "장바구니 아이템 수량이 수정되었습니다.");
            response.put("data", updatedItem);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("장바구니 수량 수정 중 오류: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "장바구니 수량을 수정하는 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 장바구니 아이템 삭제
     * DELETE /api/cart/{cartItemId}
     */
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Map<String, Object>> removeItem(
            @PathVariable Long cartItemId,
            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 세션에서 사용자 정보 조회
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);
            
            if (loginUser == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            log.info("장바구니 아이템 삭제 요청 - 사용자: {}, 아이템 ID: {}", 
                    loginUser.getId(), cartItemId);

            cartService.removeItem(cartItemId);

            response.put("success", true);
            response.put("message", "장바구니에서 상품이 제거되었습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("장바구니 아이템 삭제 중 오류: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "장바구니에서 상품을 제거하는 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 장바구니 전체 비우기
     * DELETE /api/cart/clear/all
     */
    @DeleteMapping("/clear/all")
    public ResponseEntity<Map<String, Object>> clearCart(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 세션에서 사용자 정보 조회
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);
            
            if (loginUser == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            log.info("장바구니 전체 비우기 요청 - 사용자: {}", loginUser.getId());

            cartService.clearCart(loginUser.getId());

            response.put("success", true);
            response.put("message", "장바구니가 비워졌습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("장바구니 전체 비우기 중 오류: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "장바구니를 비우는 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 장바구니 담기 요청 DTO
     */
    public static class AddToCartRequest {
        private Long productId;
        private int quantity;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    /**
     * 수량 수정 요청 DTO
     */
    public static class UpdateQuantityRequest {
        private int quantity;

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
