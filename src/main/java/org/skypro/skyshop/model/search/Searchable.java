package org.skypro.skyshop.model.search;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface Searchable {
    String getSearchTerm();

    String getName();

    UUID getId();


    @JsonIgnore
    String getContentType();
}
