package com.talentica.ecommerce.product_order.dto;

import com.talentica.ecommerce.product_order.model.Product;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Integer id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
    private String categorySlug;
    private Integer initialStock;

    public static ProductDto fromEntity(Product p, Integer stock) {
        return ProductDto.builder()
                .id(p.getId())
                .sku(p.getSku())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .active(p.getActive())
                .categorySlug(p.getCategory().getSlug())
                .initialStock(stock)
                .build();
    }
}
