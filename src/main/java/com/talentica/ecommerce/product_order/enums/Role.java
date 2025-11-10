package com.talentica.ecommerce.product_order.enums;

public enum Role {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");
    private final String displayName;

    Role(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}