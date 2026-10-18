package org.skypro.skyshop.service;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.StorageService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProductToBasket(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        Optional<Product> productOpt = storageService.getProductById(id);
        Product product = productOpt.orElseThrow(
                () -> new IllegalArgumentException("Product with id " + id + " not found")
        );

        productBasket.addProduct(id);
    }
    public UserBasket getUserBasket() {
        List<BasketItem> items = productBasket.getItems().entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> {
                    UUID id = entry.getKey();
                    int quantity = entry.getValue();

                    Product product = storageService.getProductById(id)
                            .orElseThrow(() -> new IllegalStateException(
                                    "Product with id " + id + " disappeared from storage while in basket"
                            ));

                    return new BasketItem(product, quantity);
                })
                .collect(Collectors.toList());

        return new UserBasket(items);
    }
}