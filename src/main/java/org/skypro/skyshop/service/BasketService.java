package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.StorageService;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BasketService {

    private final Map<UUID, Integer> items = new ConcurrentHashMap<>();
    private final StorageService storageService;

    public BasketService(StorageService storageService) {
        this.storageService = storageService;
    }

    public void addProductToBasket(UUID id) {
        if (id == null) {
            throw new NoSuchProductException("Product ID cannot be null");
        }

        Optional<Product> productOpt = storageService.getProductById(id);
        Product product = productOpt.orElseThrow(
                () -> new NoSuchProductException("Product with id " + id + " not found")
        );
        items.merge(id, 1, Integer::sum);
    }

    public UserBasket getUserBasket() {
        List<BasketItem> basketItems = items.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .map(entry -> {
                    UUID id = entry.getKey();
                    int quantity = entry.getValue();

                    Optional<Product> opt = storageService.getProductById(id);
                    if (opt.isEmpty()) {
                        return null;
                    }

                    Product product = opt.get();
                    return new BasketItem(product, quantity);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new UserBasket(basketItems);
    }
    public void clear() {
        items.clear();
    }
    public void removeProduct(UUID id) {
        items.remove(id);
    }
    public void setProductQuantity(UUID id, int quantity) {
        if (quantity <= 0) {
            items.remove(id);
            return;
        }
        items.put(id, quantity);
    }
    public int getTotalItemsCount() {
        return items.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}