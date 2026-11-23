package com.group.internet_store.controller;

import com.group.internet_store.dto.OrderResponse;
import com.group.internet_store.service.OrderService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> order(@RequestParam @NotBlank String orderAddress, Principal principal) {
        OrderResponse orderResponse = orderService.order(principal, orderAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(Principal principal) {
        List<OrderResponse> orders = orderService.getOrders(principal);
        return ResponseEntity.ok(orders);
    }
}
