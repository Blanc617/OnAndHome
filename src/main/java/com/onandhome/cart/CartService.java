package com.onandhome.cart;

import java.util.List;

import com.onandhome.cart.entity.CartItem;
import com.onandhome.admin.adminProduct.entity.Product;
import com.onandhome.admin.adminProduct.ProductRepository;
import com.onandhome.user.UserRepository;
import com.onandhome.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CartService {
    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;


    public List<CartItem> getCartItems(Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null)
            return List.of();
        return cartRepo.findByUser(user);
    }

    public CartItem addToCart(Long userId, Long productId, int qty) {
        User user = userRepo.findById(userId).orElseThrow();
        Product p = productRepo.findById(productId).orElseThrow();
        CartItem item = new CartItem();
        item.setUser(user);
        item.setProduct(p);
        item.setQuantity(qty);
        return cartRepo.save(item);
    }

    public void clearCart(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        cartRepo.deleteByUser(user);
    }

    public void updateQuantity(Long aLong, Long productId, int quantity) {
    }
}
