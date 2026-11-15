package com.group.internet_store.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Long id;
    private BigDecimal totalPrice;
    private List<CartItemResponse> cartItems;
}
