package com.group.internet_store.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemResponse {
    private Long productId;
    private String productName;
    //private BigDecimal productPrice;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subTotal;
}
