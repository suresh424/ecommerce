package com.suresh.ecommerce.order.service;

import com.suresh.api.model.OrderRequest;
import com.suresh.api.model.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

    List<OrderResponse> listOrders();

    OrderResponse getOrderById(Long id);
}
