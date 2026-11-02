package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.StorageService;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BasketService {

    private final StorageService storageService;
    private final ProductBasket productBasket;

    public BasketService(StorageService storageService, ProductBasket productBasket) {
        this.storageService = storageService;
        this.productBasket = productBasket;
    }

    public void addProductToBasket(UUID id) {
        if (id == null) {
            throw new NoSuchProductException("Product ID cannot be null");
        }

        Optional<Product> productOpt = storageService.getProductById(id);
        Product product = productOpt.orElseThrow(
                () -> new NoSuchProductException("Product with id " + id + " not found")
        );

        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> contents = productBasket.getItems();

        List<BasketItem> basketItems = contents.entrySet().stream()
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
        productBasket.clear();
    }

    public void removeProduct(UUID id) {
        productBasket.removeProduct(id); // если в ProductBasket есть такой метод
    }

    public void setProductQuantity(UUID id, int quantity) {
        if (quantity <= 0) {
            productBasket.removeProduct(id);
            return;
        }

        productBasket.removeProduct(id);
        for (int i = 0; i < quantity; i++) {
            productBasket.addProduct(id);
        }
    }

    public int getTotalItemsCount() {
        return productBasket.getItems().values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}