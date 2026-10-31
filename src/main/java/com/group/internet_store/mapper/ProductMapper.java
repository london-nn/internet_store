package com.group.internet_store.mapper;

import com.group.internet_store.dto.ProductRequest;
import com.group.internet_store.dto.ProductResponse;
import com.group.internet_store.dto.UserRegistrationRequest;
import com.group.internet_store.entity.Product;
import com.group.internet_store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping( target = "updatedAt", source = "updatedAt", defaultValue = "no update")
    ProductResponse productToProductResponse(Product product);

    Product productRequestToProduct(ProductRequest productRequest);

    void updateProductFromRequest(ProductRequest productRequest,@MappingTarget Product product);
}
