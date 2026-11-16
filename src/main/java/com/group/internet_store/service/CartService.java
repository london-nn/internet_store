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

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final CartMapper cartMapper;

    public CartResponse getCartByUser(Principal principal) {
        String username = principal.getName();
        User user = userRepository.getUserByUsername(username);
        Optional<Cart> optCart = cartRepository.findByUserId(user.getId());
        Cart cart = optCart.orElseGet(() -> {
            User userFromDb = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFound("User Not Found"));
            Cart newCart = new Cart();
            newCart.setUser(userFromDb);
            return cartRepository.save(newCart);
        });
        return cartMapper.cartToCartResponse(cart);
    }
}
