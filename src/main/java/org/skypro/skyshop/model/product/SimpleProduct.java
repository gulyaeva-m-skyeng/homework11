package org.skypro.skyshop.model.product;

import java.util.Objects;
import java.util.UUID;

public class SimpleProduct extends Product {

    public SimpleProduct(UUID id, String name, int price) {
            super(id, name, price);
        }

        @Override
        public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleProduct)) return false;
        SimpleProduct that = (SimpleProduct) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}