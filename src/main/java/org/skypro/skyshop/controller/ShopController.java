package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.basket.UserBasket;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping
public class ShopController {

    private final SearchService searchService;
    private final StorageService storageService;
    private final BasketService basketService;

    public ShopController(SearchService searchService, StorageService storageService, BasketService basketService) {
        this.searchService = searchService;
        this.storageService = storageService;
        this.basketService = basketService;
    }

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam(required = false) String pattern) {
        if (pattern == null || pattern.isBlank()) {
        return List.of();
    }
        return searchService.search(pattern);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return storageService.getAllProducts();
    }

    @GetMapping("/articles")
    public List<Article> getAllArticles() {
        return storageService.getAllArticles();
    }

    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        basketService.addProductToBasket(id);
        return "Продукт успешно добавлен";
    }

    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        return basketService.getUserBasket();
    }
}