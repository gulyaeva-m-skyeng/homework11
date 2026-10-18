package org.skypro.skyshop.model.product;

import java.util.Objects;
import java.util.UUID;

public class DiscountedProduct extends Product {
    private final double discountPercent;

    public DiscountedProduct(UUID id, String name, int basePrice, double discountPercent) {
        super(id, name, calculateFinalPrice(basePrice, discountPercent));

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }

        this.discountPercent = discountPercent;
    }

    private static int calculateFinalPrice(int basePrice, double discountPercent) {
        double discounted = basePrice * (100.0 - discountPercent) / 100.0;
        return (int) Math.round(discounted);
    }

    @Override
    public int getPrice() {
        return super.getPrice();
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getId() != null && getId().equals(product.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId());
    }
}