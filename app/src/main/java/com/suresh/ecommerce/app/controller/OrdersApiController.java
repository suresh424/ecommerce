package com.suresh.ecommerce.app.controller;

import com.suresh.api.OrdersApi;
import com.suresh.api.model.OrderRequest;
import com.suresh.api.model.OrderResponse;
import com.suresh.ecommerce.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrdersApiController implements OrdersApi {

    private final OrderService orderService;

    public OrdersApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderResponse> placeOrder(OrderRequest orderRequest) {
        OrderResponse response = orderService.placeOrder(orderRequest);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<List<OrderResponse>> listOrders() {
        List<OrderResponse> responses = orderService.listOrders();
        return ResponseEntity.ok(responses);
    }

}
