package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.StorageService;
import org.skypro.skyshop.model.basket.ProductBasket;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @InjectMocks
    private BasketService basketService;

    @Mock
    private StorageService storageService;

    @Mock
    private ProductBasket productBasket;

    private UUID blushId;
    private SimpleProduct blush;
    private UUID invalidId;

    @BeforeEach
    void setUp() {
        blushId = UUID.randomUUID();
        invalidId = UUID.randomUUID();

        blush = new SimpleProduct(blushId, "Румяна", 700);
    }

    // Сценарий 1
    @Test
    void addProductToBasket_whenProductNotFound_throwsException() {
        when(storageService.getProductById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class,
                () -> basketService.addProductToBasket(invalidId));

        verify(productBasket, never()).addProduct(any());
    }

    // Сценарий 2
    @Test
    void addProductToBasket_whenProductExists_callsProductBasketAddProductOnce() {
        when(storageService.getProductById(eq(blushId))).thenReturn(Optional.of(blush));

        basketService.addProductToBasket(blushId);

        verify(productBasket, times(1)).addProduct(eq(blushId));
    }

    // Сценарий 3
    @Test
    void getUserBasket_whenProductBasketIsEmpty_returnsEmptyList() {

        when(productBasket.getItems()).thenReturn(Map.of());

        var userBasket = basketService.getUserBasket();

        assertThat(userBasket.getItems()).isEmpty();
        assertThat(userBasket.getTotalPrice()).isZero();

        verify(productBasket, times(1)).getItems();
    }

    // Сценарий 4:
    @Test
    void getUserBasket_withItemsInProductBasket_returnsCorrectItems() {
        UUID id2 = UUID.randomUUID();
        SimpleProduct product2 = new SimpleProduct(id2, "Тени", 500);

        when(storageService.getProductById(eq(blushId))).thenReturn(Optional.of(blush));
        when(storageService.getProductById(eq(id2))).thenReturn(Optional.of(product2));

        Map<UUID, Integer> basketContents = Map.of(
                blushId, 2,
                id2, 4
        );
        when(productBasket.getItems()).thenReturn(basketContents);

        var userBasket = basketService.getUserBasket();

        assertThat(userBasket.getItems()).hasSize(2);
        assertThat(userBasket.getTotalPrice()).isEqualTo(3400);
        assertThat(userBasket.getCountItems()).isEqualTo(6);

        var names = userBasket.getItems().stream()
                .map(BasketItem::getName)
                .toList();
        assertThat(names).containsExactlyInAnyOrder("Румяна", "Тени");

        verify(productBasket, times(1)).getItems();
    }
}