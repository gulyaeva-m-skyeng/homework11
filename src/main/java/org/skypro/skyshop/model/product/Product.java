package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private final String name;
    private int price;

    public Product(UUID id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return name.toLowerCase();
    }

        @Override
        @JsonIgnore
        public String getContentType() {
            return "product";
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        public int getPrice () {
            return price;
        }

        @Override
        public boolean equals (Object o){
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return getId().equals(product.getId());
        }

        @Override
        public int hashCode() {
            return getId().hashCode();
        }
    }