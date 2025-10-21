package com.onandhome.order;

import com.onandhome.cart.CartItemRepository;
import com.onandhome.cart.entity.CartItem;
import com.onandhome.order.OrderRepository;
import com.onandhome.order.entity.Order;
import com.onandhome.order.entity.OrderItem;
import com.onandhome.user.UserRepository;
import com.onandhome.user.entity.User;
import com.onandhome.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자 주문 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartItemRepository cartRepo;
    private final UserRepository userRepo; // 사용자 정보를 위해 필요하다고 가정

    /**
     * 장바구니에 담긴 상품을 실제 주문으로 확정하고 저장합니다.
     * @param userId 주문을 생성하는 사용자 ID
     * @return 생성된 Order 엔티티
     */
    @Transactional
    public Order placeOrder(Long userId) {
        // 1. 사용자 정보 및 장바구니 항목 조회
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<CartItem> cartItems = cartRepo.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("장바구니에 상품이 없습니다.");
        }

        // 2. 주문(Order) 엔티티 기본 정보 생성
        Order order = new Order();
        order.setUser(user);
        order.setOrderNumber(generateOrderNumber(userId));
        order.setStatus("PENDING"); // 초기 주문 상태
        order.setCreatedAt(LocalDateTime.now());

        // 3. 주문 항목(OrderItem) 생성 및 총 금액 계산
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (CartItem ci : cartItems) {
            // OrderItem 엔티티 생성 및 데이터 설정
            OrderItem item = new OrderItem();

            // ⭐ OrderItem은 Order 엔티티를 참조해야 합니다. (ManyToOne)
            item.setOrder(order);

            // CartItem과 Product 엔티티를 통해 정보 복사
            item.setProductId(ci.getProduct().getId());
            item.setProductName(ci.getProduct().getName());
            item.setUnitPrice(ci.getProduct().getPrice());
            item.setQuantity(ci.getQuantity());

            orderItems.add(item);

            // 총 금액 계산
            totalAmount += (double) ci.getProduct().getPrice() * ci.getQuantity();
        }

        // 4. Order 엔티티에 OrderItem 엔티티 리스트와 총 금액 설정
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        // 5. DB 저장 및 카트 비우기
        Order savedOrder = orderRepo.save(order);
        cartRepo.deleteByUser(user); // 주문 완료 후 장바구니 비우기

        return savedOrder;
    }

    /**
     * 임시 주문 번호 생성 로직 (실제로는 더 정교한 UUID 또는 시퀀스를 사용해야 함)
     */
    private String generateOrderNumber(Long userId) {
        return "ORD-" + System.currentTimeMillis() + "-" + userId;
    }
}
