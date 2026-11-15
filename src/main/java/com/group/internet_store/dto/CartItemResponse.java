package com.group.internet_store.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CartItemResponse {
    private final Long productId;
    private final String productName;
    private final BigDecimal productPrice;
    private final int quantity;
    private final BigDecimal subTotal;
}
