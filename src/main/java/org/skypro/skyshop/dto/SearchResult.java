package org.skypro.skyshop.dto;

import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.model.article.Article;

public class SearchResult {
    private final String id;
    private final String name;
    private final String contentType;

    public SearchResult(String id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    public String getId()  {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    public static SearchResult fromSearchable(Searchable searchable) {
        String id = searchable.getId().toString();
        String name = searchable.getName();

        String contentType = searchable instanceof Article ? "article" : "product";
        return new SearchResult(id, name, contentType);
    }
}