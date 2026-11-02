package org.skypro.skyshop.service;

import org.springframework.stereotype.Service;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final StorageService storageService;
    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }
    public List<SearchResult> search(String pattern) {
        if (pattern == null || pattern.isBlank()) {
            return List.of();
        }

        String lowerPattern = pattern.toLowerCase().trim();

        return storageService.getAllSearchable().stream()
                .filter(Objects::nonNull)
                .distinct()
                .filter(item -> matchesPattern(item, lowerPattern))
                .map(SearchResult::fromSearchable)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private boolean matchesPattern(Searchable item, String lowerPattern) {
        String name = null;

        if (item instanceof Product p) {
            name = p.getName();
        } else if (item instanceof Article a) {
            name = a.getName();
        }
        if (name == null) {
            return false;
        }
        return name.toLowerCase().contains(lowerPattern);
    }
}