package com.group.internet_store.mapper;

import com.group.internet_store.dto.ProductRequest;
import com.group.internet_store.dto.ProductResponse;
import com.group.internet_store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse productToProductResponse(Product product);

    Product productRequestToProduct(ProductRequest productRequest);

    void updateProductFromRequest(ProductRequest productRequest,@MappingTarget Product product);
}
