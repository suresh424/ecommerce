package com.suresh.ecommerce.order.mapper;

import com.suresh.api.model.OrderRequest;
import com.suresh.api.model.OrderResponse;
import com.suresh.ecommerce.order.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toEntity(OrderRequest request);

    @Mapping(target = "orderId", source = "id")
    OrderResponse toResponse(Order order);
}
