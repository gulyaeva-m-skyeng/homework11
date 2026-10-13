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
    @JsonIgnore
    public String getSearchTerm() {
        String term = title + " " + content;
        return term != null ? term.toLowerCase() : "";
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "article";
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
    public boolean equals (Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return getId().equals(article.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}