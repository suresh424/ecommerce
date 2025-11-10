package com.talentica.ecommerce.product_order.dto;

import com.talentica.ecommerce.product_order.enums.OrderStatus;
import com.talentica.ecommerce.product_order.model.Order;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Integer id;
    private OrderStatus status;
    private BigDecimal total;
    private List<OrderItemDto> items;

    public static OrderResponseDto fromEntity(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .total(order.getTotal())
                .items(order.getItems().stream()
                        .map(OrderItemDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class OrderItemDto {
        private Integer productId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal lineTotal;

        public static OrderItemDto fromEntity(com.talentica.ecommerce.product_order.model.OrderItem oi) {
            return OrderItemDto.builder()
                    .productId(oi.getProduct().getId())
                    .productName(oi.getProduct().getName())
                    .quantity(oi.getQuantity())
                    .unitPrice(oi.getUnitPrice())
                    .lineTotal(oi.getLineTotal())
                    .build();
        }
    }
}
