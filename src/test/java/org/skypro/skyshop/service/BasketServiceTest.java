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

import java.util.Optional;
import java.util.UUID;
import java.util.List;

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

    private UUID blushId;
    private SimpleProduct blush;
    private UUID invalidId;

    @BeforeEach
    void setUp() {
        blushId = UUID.randomUUID();
        invalidId = UUID.randomUUID();

        blush = new SimpleProduct(blushId, "Румяна", 700);
    }

    // Сценарий 1. Добавление несуществующего товара в корзину приводит к выбросу исключения
    @Test
    void addProductToBasket_whenProductNotFound_throwsException() {
        when(storageService.getProductById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class,
                () -> basketService.addProductToBasket(invalidId));

        var basket = basketService.getUserBasket();
        assertThat(basket.getItems()).isEmpty();
    }

    // Сценарий 2. Добавление существующего товара вызывает метод addProduct у мока ProductBasket
    @Test
    void addProductToBasket_whenProductExists_addsItemToBasket() {
        when(storageService.getProductById(eq(blushId))).thenReturn(Optional.of(blush));

        basketService.addProductToBasket(blushId);

        verify(storageService, times(1)).getProductById(eq(blushId));

        var userBasket = basketService.getUserBasket();
        assertThat(userBasket.getItems()).hasSize(1);

        BasketItem item = userBasket.getItems().get(0);
        assertThat(item.getName()).isEqualTo("Румяна");
        assertThat(item.getPrice()).isEqualTo(700);
        assertThat(item.getQuantity()).isEqualTo(1);
        assertThat(item.getTotal()).isEqualTo(700);
    }

    // Сценарий 3. Метод getUserBacket возвращает пустую корзину, если ProductBasket пуст.
    @Test
    void getUserBasket_whenEmpty_returnsEmptyList() {
        var emptyBasket = basketService.getUserBasket();

        assertThat(emptyBasket.getItems()).isEmpty();
    }

    // Сценарий 4. Метод getUserBasket возвращает подходящую корзину, если в ProductBasket есть товары
    @Test
    void getUserBasket_withItems_returnsCorrectItemsAndCallsStorage() {
        UUID id2 = UUID.randomUUID();
        SimpleProduct product2 = new SimpleProduct(id2, "Тени", 500);

        // Настроим моки для обоих товаров
        when(storageService.getProductById(eq(blushId))).thenReturn(Optional.of(blush));
        when(storageService.getProductById(eq(id2))).thenReturn(Optional.of(product2));

        basketService.addProductToBasket(blushId);
        basketService.addProductToBasket(id2);

        var userBasket = basketService.getUserBasket();

        assertThat(userBasket.getItems()).hasSize(2);

        List<BasketItem> items = userBasket.getItems();
        long total = 0;
        for (BasketItem item : items) {
            total += (long) item.getPrice() * item.getQuantity();
        }
        assertThat(total).isEqualTo(1200L);
    }
}