package com.talentica.ecommerce.product_order.service.impl;

import com.talentica.ecommerce.product_order.dto.*;
import com.talentica.ecommerce.product_order.enums.OrderStatus;
import com.talentica.ecommerce.product_order.model.*;
import com.talentica.ecommerce.product_order.repository.*;
import com.talentica.ecommerce.product_order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private InventoryRepository inventoryRepository;

    @Transactional
    @Override
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Order order = new Order();
        order.setCustomer(dto.getCustomer());
        order.setStatus(OrderStatus.CREATED);
        order.setRequestId(UUID.randomUUID().toString());

        List<OrderItem> items = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (OrderRequestDto.Item i : dto.getItems()) {
            Product product = productRepository.findById(i.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            Inventory inv = inventoryRepository.findByProduct(product)
                    .orElseThrow(() -> new RuntimeException("Inventory not found"));

            if (inv.getQuantity() < i.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + product.getName());
            }

            inv.setQuantity(inv.getQuantity() - i.getQuantity());
            inventoryRepository.save(inv);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(i.getQuantity());
            item.setUnitPrice(product.getPrice());
            item.setLineTotal(product.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
            items.add(item);
            subtotal = subtotal.add(item.getLineTotal());
        }

        order.setSubtotal(subtotal);
        order.setTax(subtotal.multiply(BigDecimal.valueOf(0.1))); // 10% tax
        order.setShippingFee(BigDecimal.valueOf(50));
        order.setTotal(order.getSubtotal().add(order.getTax()).add(order.getShippingFee()));

        order.setItems(items);
        Order saved = orderRepository.save(order);

        return OrderResponseDto.fromEntity(saved);
    }

    @Override
    public OrderResponseDto getOrderById(Integer id) {
        return orderRepository.findById(id)
                .map(OrderResponseDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<OrderResponseDto> getOrdersForCurrentUser() {
        // You can later integrate with SecurityContext to fetch current user
        return orderRepository.findAll().stream()
                .map(OrderResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Integer orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
    }
}
