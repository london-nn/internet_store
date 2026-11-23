package com.group.internet_store.mapper;

import com.group.internet_store.dto.CartItemResponse;
import com.group.internet_store.dto.CartResponse;
import com.group.internet_store.dto.OrderItemResponse;
import com.group.internet_store.dto.OrderResponse;
import com.group.internet_store.entity.Order;
import com.group.internet_store.entity.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse orderToOrderResponse(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponse orderItemToOrderItemResponse(OrderItem orderItem);
}
