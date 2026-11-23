package com.group.internet_store.service;

import com.group.internet_store.dto.OrderItemResponse;
import com.group.internet_store.dto.OrderResponse;
import com.group.internet_store.entity.*;
import com.group.internet_store.exceptions.CartNotFound;
import com.group.internet_store.exceptions.UserNotFound;
import com.group.internet_store.mapper.OrderMapper;
import com.group.internet_store.repository.CartRepository;
import com.group.internet_store.repository.OrderRepository;
import com.group.internet_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse order(Principal principal,String orderAddress){
        String username = principal.getName();
        User user = userRepository.getOptionalUserByUsername(username).orElseThrow(() -> new UserNotFound("User not found"));
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new CartNotFound("Cart not found"));
        List<CartItem> cartItems = cart.getCartItems();
        if(cartItems == null || cartItems.isEmpty()){
            throw new CartNotFound("Cart not found");
        }
        Order order = new Order();
        order.setUser(user);
        order.setOrderAddress(orderAddress);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("CREATED");
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setSubTotal(orderItem.getPrice().multiply(java.math.BigDecimal.valueOf(orderItem.getQuantity())));
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        if(order.getOrderItems() == null) {
            order.setTotalPrice(BigDecimal.ZERO);
        }
        else {
            BigDecimal totalPrice = order.getOrderItems().stream()
                    .map(OrderItem::getSubTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setTotalPrice(totalPrice);
        }
        Order savedOrder = orderRepository.save(order);
        cart.getCartItems().clear();
        return orderMapper.orderToOrderResponse(savedOrder);
    }

    public List<OrderResponse> getOrders(Principal principal) {
        String username = principal.getName();
        User user = userRepository.getOptionalUserByUsername(username).orElseThrow(() -> new UserNotFound("User not found"));
        return orderRepository.findAllByUserId(user.getId()).stream()
                .map(orderMapper::orderToOrderResponse)
                .toList();
    }
}
