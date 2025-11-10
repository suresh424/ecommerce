package com.talentica.ecommerce.product_order.dto;

import com.talentica.ecommerce.product_order.model.User;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private User customer;
    private List<Item> items;

    @Getter
    @Setter
    public static class Item {
        private Integer productId;
        private Integer quantity;
    }
}
