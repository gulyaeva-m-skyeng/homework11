package org.skypro.skyshop.model.basket;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@SessionScope
public class ProductBasket {

    private final Map<UUID, Integer> items = new HashMap<>();

    public void addProduct(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        items.computeIfAbsent(id, key -> 0);
        items.put(id, items.get(id) + 1);
    }

    public Map<UUID, Integer> getItems() {
        return Collections.unmodifiableMap(new HashMap<>(items));
    }
}