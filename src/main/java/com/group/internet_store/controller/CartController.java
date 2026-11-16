package com.group.internet_store.controller;

import com.group.internet_store.dto.CartResponse;
import com.group.internet_store.entity.User;
import com.group.internet_store.repository.UserRepository;
import com.group.internet_store.security.CustomUserDetailsService;
import com.group.internet_store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    ResponseEntity<CartResponse> getCart(Principal principal) {
        CartResponse cartResponse = cartService.getCartByUser(principal);
        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping
    ResponseEntity<CartResponse> addProductToCart(Principal principal, @RequestParam Long productId,@RequestParam int quantity) {
        CartResponse cartResponse = cartService.addProductToCart(principal, productId, quantity);
        return ResponseEntity.ok(cartResponse);
    }
}
