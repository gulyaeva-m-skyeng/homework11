package org.skypro.skyshop.service;

import org.springframework.stereotype.Service;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.List;
import java.util.Objects;

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

        String lowerPattern = pattern.toLowerCase();

        return storageService.getAllSearchable().stream()
                .filter(Objects::nonNull)
                .distinct()
                .map(SearchResult::fromSearchable)
                .filter(Objects::nonNull)
                .toList();
    }
}