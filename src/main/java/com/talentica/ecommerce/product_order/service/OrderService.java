package com.talentica.ecommerce.product_order.service;

import com.talentica.ecommerce.product_order.dto.OrderRequestDto;
import com.talentica.ecommerce.product_order.dto.OrderResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto dto);
    OrderResponseDto getOrderById(Integer id);
    List<OrderResponseDto> getOrdersForCurrentUser();
    void updateOrderStatus(Integer orderId, String status);
}
