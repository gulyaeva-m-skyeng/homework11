package org.skypro.skyshop.service;

import org.skypro.skyshop.dto.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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

        String lowerPattern = pattern.toLowerCase();

        return storageService.getAllSearchable().stream()
                .filter(s -> s.getSearchTerm() != null && s.getSearchTerm().toLowerCase().contains(lowerPattern))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}