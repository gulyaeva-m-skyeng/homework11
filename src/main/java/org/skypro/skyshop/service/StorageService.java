package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;

import org.springframework.stereotype.Service;
import org.skypro.skyshop.service.StorageService;
import java.util.concurrent.ConcurrentHashMap;

import java.util.*;

@Service
public class StorageService {

    private final Set<Searchable> allItems = ConcurrentHashMap.newKeySet();

    private final Map<UUID, Product> products = new HashMap<>();
    private final Map<UUID, Article> articles = new HashMap<>();

    public StorageService() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();

        add(new SimpleProduct(id1, "Румяна", 700));
        add(new SimpleProduct(id2, "Помада", 1000));
        add(new FixPriceProduct(id3, "Карандаш", 169));

        Product discounted = new DiscountedProduct(
                UUID.randomUUID(),
                "Подводка со скидкой",
                200,
                20.0
        );
        add(discounted);

        add(new Article(UUID.randomUUID(), "Выбор румян под тип кожи и оттенок лица", "Какие оттенки румян подходят для светлой, средней и смуглой кожи"));
        add(new Article(UUID.randomUUID(), "Как выбрать помаду с ароматом ванили", "В этой статье мы расскажем какие помады бывают"));
    }

    public void add(Searchable item) {
        if (item == null) {
            return;
        }
        if (item instanceof Product p) {
            if (p.getId() == null) {
                throw new IllegalArgumentException("Product must have non-null ID");
            }
            products.put(p.getId(), p);
            allItems.add(p);
        } else if (item instanceof Article a) {
            if (a.getId() == null) {
                throw new IllegalArgumentException("Article must have non-null ID");
            }
            articles.put(a.getId(), a);
            allItems.add(a);
        } else {
            throw new IllegalArgumentException("Unsupported Searchable type: " + item.getClass());
        }
    }

    public Set<Searchable> getAllSearchable() {
        return Collections.unmodifiableSet(allItems);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public List<Article> getAllArticles() {
        return new ArrayList<>(articles.values());
    }

    public Product getProductByIdOrFail(UUID id) {
        if (id == null) {
            throw new NoSuchProductException("Product ID cannot be null");
        }
        Product product = products.get(id);
        if (product == null) {
            throw new NoSuchProductException("Product with id " + id + " not found");
        }
        return product;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public Optional<Article> getArticleById(UUID id) {
        return Optional.ofNullable(articles.get(id));
    }

    public Optional<Searchable> getById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }

        Optional<Product> pOpt = getProductById(id);
        if (pOpt.isPresent()) {
            return pOpt.map(p -> (Searchable) p);
        }

        Optional<Article> aOpt = getArticleById(id);
        if (aOpt.isPresent()) {
            return aOpt.map(a -> (Searchable) a);
        }

        return Optional.empty();
    }

    public boolean removeProductById(UUID id) {
        Product removed = products.remove(id);
        if (removed != null) {
            allItems.remove(removed);
            return true;
        }
        return false;
    }

    public boolean removeArticleById(UUID id) {
        Article removed = articles.remove(id);
        if (removed != null) {
            allItems.remove(removed);
            return true;
        }
        return false;
    }

    public void clear() {
        products.clear();
        articles.clear();
        allItems.clear();
    }

    public int size() {
        return allItems.size();
    }
}