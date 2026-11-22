package com.group.internet_store.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String id;
    private String orderDate;
    private String orderStatus;
    private String orderAddress;
    private BigDecimal totalPrice;
    private List<OrderItemResponse> orderItems;
}
