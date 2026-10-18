package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private final String name;
    private final int price;

    public Product(UUID id, String name, int price) {
        if (id == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPrice () {
            return price;
        }

    @Override
    public String getSearchTerm() {
        return (name + " " + price).toLowerCase();
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
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}