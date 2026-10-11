package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;

public class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String content;

    public Article(UUID id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return title;
    }

    @JsonIgnore
    public String getContent() {
        return content;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        String combined = title + " " + content;
        return combined.toLowerCase();
    }
}