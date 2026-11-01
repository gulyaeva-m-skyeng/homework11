package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.service.StorageService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private StorageService storageService;

    private UUID blushId, lipstickId, eyelinerId, mascaraId;
    private SimpleProduct blush, lipstick;
    private FixPriceProduct eyeliner;
    private DiscountedProduct mascara;
    private Article articleBlush, articleLipstick;

    @BeforeEach
    void setUp() {
        blushId = UUID.randomUUID();
        lipstickId = UUID.randomUUID();
        eyelinerId = UUID.randomUUID();
        mascaraId = UUID.randomUUID();

        blush = new SimpleProduct(blushId, "Румяна", 700);
        lipstick = new SimpleProduct(lipstickId, "Помада", 1000);
        eyeliner = new FixPriceProduct(eyelinerId, "Карандаш", 169);
        mascara = new DiscountedProduct(mascaraId, "Тушь со скидкой", 200, 20.0);

        articleBlush = new Article(UUID.randomUUID(), "Выбор румян под тип кожи и оттенок лица", "Какие оттенки румян подходят для светлой, средней и смуглой кожи");
        articleLipstick = new Article(UUID.randomUUID(), "Как выбрать помаду с ароматом ванили", "В этой статье мы расскажем какие помады бывают");
    }

    // Сценарий 1: Поиск в случае отсутствия объектов в StorageService
    @Test
    void search_whenStorageIsEmpty_returnsEmptyList() {
        when(storageService.getAllSearchable()).thenReturn(new HashSet<>());

        List<SearchResult> results = searchService.search("Румяна");

        assertThat(results).isEmpty();
        verify(storageService, times(1)).getAllSearchable();
    }

    // Сценарий 2: Поиск в случае, если объекты в StorageService есть, но нет подходящего.
    @Test
    void search_whenItemsExistButNoMatch_returnsEmptyList() {
        List<Searchable> all = List.of(blush, lipstick, eyeliner, mascara, articleBlush, articleLipstick);
        when(storageService.getAllSearchable()).thenReturn(new HashSet<>(all));

        List<SearchResult> results = searchService.search("Хайлайтер");

        assertThat(results).isEmpty();
        verify(storageService, times(1)).getAllSearchable();
    }

    // Сценарий 3: Поиск, когда есть подходящий объект в StorageService
    @Test
    void search_withMatchingObjects_returnsCorrectResults() {
        List<Searchable> all = List.of(blush, lipstick, eyeliner, mascara, articleBlush, articleLipstick);
        when(storageService.getAllSearchable()).thenReturn(new HashSet<>(all));

        List<SearchResult> results = searchService.search("Румяна");

        assertThat(results).hasSizeGreaterThanOrEqualTo(1);

        assertThat(results)
                .extracting(SearchResult::getName)
                .contains("Румяна", "Выбор румян под тип кожи и оттенок лица");
    }
}