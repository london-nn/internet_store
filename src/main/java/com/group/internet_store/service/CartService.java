package com.group.internet_store.service;

import com.group.internet_store.dto.CartResponse;
import com.group.internet_store.entity.Cart;
import com.group.internet_store.entity.CartItem;
import com.group.internet_store.entity.Product;
import com.group.internet_store.entity.User;
import com.group.internet_store.exceptions.MustBeGreaterThanZero;
import com.group.internet_store.exceptions.ProductNotFound;
import com.group.internet_store.exceptions.UserNotFound;
import com.group.internet_store.mapper.CartMapper;
import com.group.internet_store.repository.CartRepository;
import com.group.internet_store.repository.ProductRepository;
import com.group.internet_store.repository.UserRepository;
import jakarta.transaction.Transactional;
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
        User user = getUserFromPrincipal(principal);
        Optional<Cart> optCart = cartRepository.findByUserId(user.getId());
        Cart cart = optCart.orElseGet(() -> {
            User userFromDb = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFound("User Not Found"));
            Cart newCart = new Cart();
            newCart.setUser(userFromDb);
            return cartRepository.save(newCart);
        });
        return cartMapper.cartToCartResponse(cart);
    }

    @Transactional
    public CartResponse addProductToCart(Principal principal, Long productId, int quantity) {
        if(quantity <= 0){
            throw new MustBeGreaterThanZero("Quantity must be positive and greater than 0");
        }
        User user = getUserFromPrincipal(principal);
        Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
        Optional<CartItem> optCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst();
        if(optCartItem.isPresent()) {
            CartItem cartItem = optCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFound("Product Not Found"));
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getCartItems().add(cartItem);

        }
        cartRepository.save(cart);
        return cartMapper.cartToCartResponse(cart);
    }
    public User getUserFromPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.getOptionalUserByUsername(username).orElseThrow(() -> new UserNotFound("User Not Found"));
    }
}
