package org.skypro.skyshop.model.product;

import java.util.Objects;
import java.util.UUID;

public class DiscountedProduct extends Product {
    private final double discountPercent;

    public DiscountedProduct(UUID id, String name, int basePrice, double discountPercent) {
        super(id, name, basePrice);
        this.discountPercent = discountPercent;
    }

    @Override
    public int getPrice() {
        int basePrice = super.getPrice();
        return (int) Math.round(basePrice * (1 - discountPercent / 100.0));
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Product)) return false;
            Product product = (Product) o;
            return Objects.equals(getId(), product.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId());
    }
}
