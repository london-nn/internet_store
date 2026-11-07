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

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "MMMM d, yyyy - H:mm:ss")
    @Mapping(target = "updatedAt", source = "updatedAt", dateFormat = "MMMM d, yyyy - H:mm:ss", defaultValue = "no update")
    ProductResponse productToProductResponse(Product product);

    Product productRequestToProduct(ProductRequest productRequest);

    void updateProductFromRequest(ProductRequest productRequest,@MappingTarget Product product);
}
