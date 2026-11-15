package com.group.internet_store.mapper;

import com.group.internet_store.dto.CartItemResponse;
import com.group.internet_store.dto.CartResponse;
import com.group.internet_store.entity.Cart;
import com.group.internet_store.entity.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse cartToCartResponse(Cart cart);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price" , target = "productPrice")
    @Mapping(target = "subTotal", expression = "java(cartItem.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(cartItem.getQuantity())))")
    CartItemResponse cartItemToCartItemResponse(CartItem cartItem);

    @AfterMapping
    default void calculateTotalPrice(@MappingTarget CartResponse cartResponse) {
        if(cartResponse.getCartItems() == null) {
            cartResponse.setTotalPrice(BigDecimal.ZERO);
            return;
        }
        BigDecimal totalPrice = cartResponse.getCartItems().stream()
                .map(CartItemResponse::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cartResponse.setTotalPrice(totalPrice);
    }
}
