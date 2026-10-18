package org.skypro.skyshop.model.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;
import java.util.Objects;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SearchResult {
    private final UUID id;
    private final String name;
    private final String contentType;

    public SearchResult(UUID id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    public UUID getId()  {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    public static SearchResult fromSearchable(Searchable searchable) {
        if (searchable == null) {
            return null;
        }
        UUID id = searchable.getId();
            String name;
            String contentType;

            if (searchable instanceof Product p) {
                name = p.getName();
                contentType = "product";

            } else if (searchable instanceof Article a) {
                name = a.getName();
                contentType = "article";
            } else {
                name = "Unknown item";
                contentType = "unknown";
            }

        return new SearchResult(id, name, contentType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchResult)) return false;
        SearchResult that = (SearchResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}