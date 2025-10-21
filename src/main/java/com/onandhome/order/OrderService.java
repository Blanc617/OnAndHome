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
import java.util.stream.Collectors;

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
        //1. 사용자 및 장바구나 아이템 조회
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalStateException("해당 유저를 찾을 수 없습니다."));
        List<CartItem> cartItems = cartRepo.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어 있습니다");
        }

        //2. CartItem 목록을 OrderItem 목록으로 변환
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> OrderItem.createOrderItem(
                        cartItem.getProduct(),
                        cartItem.getProduct().getPrice(),
                      cartItem.getQuantity()   //CartItem에 getCount 수량 메소드 만들어주세요
                ))
                .collect(Collectors.toList());
        //3. 주문 생성
        Order order = Order.create(user, orderItems);

        //4. 주문 저장
        Order savedOrder = orderRepo.save(order);

        //5. 장바구니 비우기
        cartRepo.deleteByUser(user);

        return order;
    }
    /**
     * 특정 사용자의 모든 주문 목록을 조회합니다.
     * @param userId 사용자의 ID
     * @return 주문 목록
     */
    public List<Order> getOrders(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalStateException("해당 유저를 찾을 수 없습니다."));
        return orderRepo.findByUser(user);
    }
    /**
     * 특정 주문의 상세 정보를 조회합니다.
     * @param orderId 주문 ID
     * @return 주문 정보 (없으면 null 반환)
     */
    public Order getOrder(Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    @Transactional
    public Order pay(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("해당 주문을 찾을 수 없습니다."));
        order.pay();
        return order;
    }

    @Transactional(readOnly = true)
    public Order detail(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("해당 주문을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public String track(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("해당 주문을 찾을 수 없습니다."));
        return switch (order.getStatus()) {
            case ORDERED -> "상품 준비중";
            case DELIVERING -> "배송중";
            case DELIVERED -> "배송완료";
            case CANCELED -> "주문 취소";
            default -> "알수 없음";
        };
    }
}
