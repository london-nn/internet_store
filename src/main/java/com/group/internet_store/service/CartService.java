package com.group.internet_store.service;

import com.group.internet_store.dto.CartResponse;
import com.group.internet_store.entity.Cart;
import com.group.internet_store.entity.CartItem;
import com.group.internet_store.entity.User;
import com.group.internet_store.exceptions.UserNotFound;
import com.group.internet_store.mapper.CartMapper;
import com.group.internet_store.repository.CartRepository;
import com.group.internet_store.repository.ProductRepository;
import com.group.internet_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final CartMapper cartMapper;

    public CartResponse getCartByUser(Long userId) {
        Optional<Cart> optCart = cartRepository.findByUserId(userId);
        Cart cart = optCart.orElseGet(() -> {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User Not Found"));
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
        return cartMapper.cartToCartResponse(cart);
    }
}
