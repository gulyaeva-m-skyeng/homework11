package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;
import java.util.Objects;

public class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String content;

    public Article(UUID id, String title, String content) {
        if (id == null) {
            throw new IllegalArgumentException("Article id cannot be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Article title cannot be blank");
        }

        this.id = id;
        this.title = title;
        this.content = (content != null) ? content : "";
    }

    @Override
    public String getSearchTerm() {
        return (title + " " + content).toLowerCase();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return title;
    }

    @JsonIgnore
    public String getContentType() {
        return "article";
    }

    @JsonIgnore
    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return getId() != null && getId().equals(article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}