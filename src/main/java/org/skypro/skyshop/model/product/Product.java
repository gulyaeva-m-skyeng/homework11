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

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return name.toLowerCase();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}