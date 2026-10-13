package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
public class StorageService {

    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        loadTestData();
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public List<Article> getAllArticles() {
            return new ArrayList<>(articles.values());
    }

    public List<Searchable> getAllSearchable() {
        List<Searchable> all = new ArrayList<>();
        all.addAll(products.values());
        all.addAll(articles.values());
        return all;
    }

    private void loadTestData() {

        Product p1 = new SimpleProduct(UUID.randomUUID(), "Румяна", 700);
        Product p2 = new SimpleProduct(UUID.randomUUID(), "Помада", 1000);
        Product p3 = new FixPriceProduct(UUID.randomUUID(), "Карандаш", 169);

        products.put(p1.getId(), p1);
        products.put(p2.getId(), p2);
        products.put(p3.getId(), p3);

        Product discounted = new DiscountedProduct(
                UUID.randomUUID(),
                "Подводка со скидкой",
                200,
                20.0
        );
        products.put(discounted.getId(), discounted);

        Article a1 = new Article(UUID.randomUUID(), "Выбор румян под тип кожи и оттенок лица", "Какие оттенки румян подходят для светлой, средней и смуглой кожи");
        Article a2 = new Article(UUID.randomUUID(), "Как выбрать помаду с ароматом ванили", "В этой статье мы расскажем какие помады бывают");

        articles.put(a1.getId(), a1);
        articles.put(a2.getId(), a2);
    }
}