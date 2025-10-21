package com.onandhome.order;

import com.onandhome.order.dto.CreateOrderRequest;
import com.onandhome.order.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성 API
     * POST /api/orders/create
     * 
     * 요청 예시:
     * {
     *   "userId": 1,
     *   "orderItems": [
     *     {
     *       "productId": 1,
     *       "quantity": 2
     *     },
     *     {
     *       "productId": 2,
     *       "quantity": 1
     *     }
     *   ]
     * }
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody CreateOrderRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("주문 생성 API 호출: userId={}", request.getUserId());
            
            OrderDTO orderDTO = orderService.createOrder(request);
            response.put("success", true);
            response.put("message", "주문이 생성되었습니다.");
            response.put("data", orderDTO);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.warn("주문 생성 실패: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error("주문 생성 중 오류: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "주문 생성 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 사용자의 모든 주문 조회
     * GET /api/orders/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserOrders(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("사용자 주문 목록 조회: userId={}", userId);
            
            List<OrderDTO> orders = orderService.getOrders(userId);
            response.put("success", true);
            response.put("data", orders);
            response.put("count", orders.size());
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("주문 조회 실패: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            log.error("주문 조회 중 오류: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "주문 조회 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 주문 상세 조회
     * GET /api/orders/{orderId}
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrder(@PathVariable Long orderId) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("주문 상세 조회: orderId={}", orderId);
            
            OrderDTO order = orderService.getOrder(orderId);
            response.put("success", true);
            response.put("data", order);
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("주문 조회 실패: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            log.error("주문 조회 중 오류: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "주문 조회 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 주문 결제 처리
     * POST /api/orders/{orderId}/pay
     */
    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Map<String, Object>> pay(@PathVariable Long orderId) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("주문 결제 처리: orderId={}", orderId);
            
            OrderDTO orderDTO = orderService.pay(orderId);
            response.put("success", true);
            response.put("message", "결제가 완료되었습니다.");
            response.put("data", orderDTO);
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("결제 실패: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            log.error("결제 중 오류: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "결제 처리 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 주문 취소
     * POST /api/orders/{orderId}/cancel
     */
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Map<String, Object>> cancel(@PathVariable Long orderId) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("주문 취소 요청: orderId={}", orderId);
            
            OrderDTO orderDTO = orderService.cancel(orderId);
            response.put("success", true);
            response.put("message", "주문이 취소되었습니다.");
            response.put("data", orderDTO);
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("주문 취소 실패: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalStateException e) {
            log.warn("주문 취소 불가: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error("주문 취소 중 오류: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "주문 취소 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 주문 배송 상태 조회
     * GET /api/orders/{orderId}/track
     */
    @GetMapping("/{orderId}/track")
    public ResponseEntity<Map<String, Object>> track(@PathVariable Long orderId) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("주문 배송 상태 조회: orderId={}", orderId);
            
            String trackingStatus = orderService.track(orderId);
            response.put("success", true);
            response.put("data", trackingStatus);
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("배송 상태 조회 실패: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            log.error("배송 상태 조회 중 오류: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "배송 상태 조회 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 장바구니 기반 주문 생성 (기존 방식)
     * POST /api/orders/cart/create
     */
    @PostMapping("/cart/create")
    public ResponseEntity<Map<String, Object>> createFromCart(@RequestParam Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("장바구니 기반 주문 생성: userId={}", userId);
            
            OrderDTO orderDTO = orderService.createOrderFromCart(userId);
            response.put("success", true);
            response.put("message", "주문이 생성되었습니다.");
            response.put("data", orderDTO);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.warn("주문 생성 실패: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error("주문 생성 중 오류: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "주문 생성 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
