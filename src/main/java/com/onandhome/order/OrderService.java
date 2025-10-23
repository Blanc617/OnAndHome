package com.onandhome.order;

import com.onandhome.admin.adminProduct.entity.Product;
import com.onandhome.admin.adminProduct.ProductRepository;
import com.onandhome.cart.CartItemRepository;
import com.onandhome.cart.entity.CartItem;
import com.onandhome.order.dto.CreateOrderRequest;
import com.onandhome.order.dto.OrderDTO;
import com.onandhome.order.entity.Order;
import com.onandhome.order.entity.OrderItem;
import com.onandhome.user.UserRepository;
import com.onandhome.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final CartItemRepository cartRepo;

    /**
     * 모든 주문 조회 (관리자용)
     * 생성 순서 최신순으로 정렬
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        return orders.stream()
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자의 모든 주문 목록 조회
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrders(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<Order> orders = orderRepo.findByUserOrderByCreatedAtDesc(user);
        return orders.stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 주문의 상세 정보 조회
     */
    @Transactional(readOnly = true)
    public OrderDTO getOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        return OrderDTO.fromEntity(order);
    }

    /**
     * 주문 생성 (DTO 기반)
     *
     * @param request 주문 생성 요청
     * @return 생성된 주문 DTO
     */
    public OrderDTO createOrder(CreateOrderRequest request) {
        log.info("주문 생성 요청: userId={}", request.getUserId());

        // 1. 사용자 조회
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 2. 주문 항목 검증 및 생성
        if (request.getOrderItems() == null || request.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("주문 항목이 최소 1개 이상이어야 합니다.");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getOrderItems()) {
            // 상품 조회
            Product product = productRepo.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다: " + itemRequest.getProductId()));

            // 수량 검증
            if (itemRequest.getQuantity() <= 0) {
                throw new IllegalArgumentException("주문 수량은 1 이상이어야 합니다.");
            }

            // 재고 확인 및 OrderItem 생성
            try {
                OrderItem orderItem = OrderItem.createOrderItem(
                        product,
                        product.getPrice(),
                        itemRequest.getQuantity()
                );
                orderItems.add(orderItem);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("상품 " + product.getName() + ": " + e.getMessage());
            }
        }

        // 3. 주문 생성
        Order order = Order.create(user, orderItems);

        // 4. 주문 저장
        Order savedOrder = orderRepo.save(order);
        log.info("주문 생성 완료: orderId={}, orderNumber={}", savedOrder.getId(), savedOrder.getOrderNumber());

        return OrderDTO.fromEntity(savedOrder);
    }

    /**
     * 장바구니 기반 주문 생성 (기존 로직)
     */
    public OrderDTO createOrderFromCart(Long userId) {
        log.info("장바구니 기반 주문 생성: userId={}", userId);

        // 1. 사용자 및 장바구니 아이템 조회
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        List<CartItem> cartItems = cartRepo.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어 있습니다.");
        }

        // 2. CartItem 목록을 OrderItem 목록으로 변환
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> OrderItem.createOrderItem(
                        cartItem.getProduct(),
                        cartItem.getProduct().getPrice(),
                        cartItem.getQuantity()
                ))
                .collect(Collectors.toList());

        // 3. 주문 생성
        Order order = Order.create(user, orderItems);

        // 4. 주문 저장
        Order savedOrder = orderRepo.save(order);

        // 5. 장바구니 비우기
        cartRepo.deleteByUser(user);

        log.info("장바구니 기반 주문 생성 완료: orderId={}", savedOrder.getId());
        return OrderDTO.fromEntity(savedOrder);
    }

    /**
     * 주문 결제 처리
     */
    public OrderDTO pay(Long orderId) {
        log.info("주문 결제 처리: orderId={}", orderId);

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        order.pay(); // paidAt 자동 설정
        Order savedOrder = orderRepo.save(order);

        log.info("주문 결제 완료: orderId={}", orderId);
        return OrderDTO.fromEntity(savedOrder);
    }

    /**
     * 주문 취소
     */
    public OrderDTO cancel(Long orderId) {
        log.info("주문 취소 요청: orderId={}", orderId);

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        try {
            order.cancel();
            Order savedOrder = orderRepo.save(order);
            log.info("주문 취소 완료: orderId={}", orderId);
            return OrderDTO.fromEntity(savedOrder);
        } catch (IllegalStateException e) {
            log.warn("주문 취소 실패: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 주문 배송 상태 조회
     */
    @Transactional(readOnly = true)
    public String track(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        return switch (order.getStatus()) {
            case ORDERED -> "주문 완료";
            case DELIVERING -> "배송중";
            case DELIVERED -> "배송완료";
            case CANCELED -> "주문 취소";
            default -> "알 수 없음";
        };
    }

    /**
     * 장바구니 확인 (결제 전)
     */
    @Transactional(readOnly = true)
    public List<CartItem> checkout(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return cartRepo.findByUser(user);
    }
}
