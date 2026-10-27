package org.skypro.skyshop.model.basket;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductBasket {

    private final Map<UUID, Integer> items = new ConcurrentHashMap<>();

    public void addProduct(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        items.merge(id, 1, Integer::sum);
    }

    public Map<UUID, Integer> getItems() {
        return Collections.unmodifiableMap(new HashMap<>(items));
    }
}