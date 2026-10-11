package org.skypro.skyshop.controller;

import org.skypro.skyshop.dto.SearchResult;
import org.skypro.skyshop.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopController {
    private final SearchService searchService;

    public ShopController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam(required = false) String pattern) {
        return searchService.search(pattern);
    }
}