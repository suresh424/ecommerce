package com.suresh.ecommerce.product.mapper;

import com.suresh.api.model.ProductRequest;
import com.suresh.api.model.ProductResponse;
import com.suresh.ecommerce.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product entity);
}

