package com.suresh.ecommerce.order.service;

import com.suresh.api.model.OrderRequest;
import com.suresh.api.model.OrderResponse;
import com.suresh.ecommerce.commons.exception.ResourceNotFoundException;
import com.suresh.ecommerce.order.enums.OrderStatus;
import com.suresh.ecommerce.order.mapper.OrderMapper;
import com.suresh.ecommerce.order.model.Order;
import com.suresh.ecommerce.order.model.OrderItem;
import com.suresh.ecommerce.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        // TODO later: fetch product prices from product-service
        double defaultUnitPrice = 100.0;

        Order order = orderMapper.toEntity(request);
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> items = new ArrayList<>();
        double total = 0.0;

        for (com.suresh.api.model.OrderItem itemReq : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(itemReq.getProductId().longValue());
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(defaultUnitPrice);

            double lineTotal = defaultUnitPrice * itemReq.getQuantity();
            item.setLineTotal(lineTotal);

            total += lineTotal;
            items.add(item);
        }

        order.setItems(items);
        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);

        return orderMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> listOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found: " + id));
        return orderMapper.toResponse(order);
    }
}
