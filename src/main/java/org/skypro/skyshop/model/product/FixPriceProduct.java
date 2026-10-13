package org.skypro.skyshop.model.product;

import java.util.Objects;
import java.util.UUID;

public class FixPriceProduct extends Product {
    public FixPriceProduct(UUID id, String name, int fixedPrice) {
        super(id, name, fixedPrice);
    }

    @Override
    public int getPrice() {
        return super.getPrice();
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