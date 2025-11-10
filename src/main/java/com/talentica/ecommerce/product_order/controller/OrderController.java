package com.talentica.ecommerce.product_order.controller;

import com.talentica.ecommerce.product_order.dto.OrderRequestDto;
import com.talentica.ecommerce.product_order.dto.OrderResponseDto;
import com.talentica.ecommerce.product_order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody OrderRequestDto dto,
            @AuthenticationPrincipal UserDetails principal
    ) {

        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> myOrders() {
        return ResponseEntity.ok(orderService.getOrdersForCurrentUser());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/orders/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
