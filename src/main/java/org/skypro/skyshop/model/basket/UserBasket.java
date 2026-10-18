package org.skypro.skyshop.model.basket;

import java.util.Collections;
import java.util.List;

public class UserBasket {
    private final List<BasketItem> items;
    private final int totalPrice;
    private final int countItems;

    public UserBasket(List<BasketItem> items) {

        this.items = Collections.unmodifiableList(items);
        this.totalPrice = items.stream()
                .mapToInt(BasketItem::getTotal)
                .sum();

        this.countItems = items.stream()
                .mapToInt(BasketItem::getQuantity)
                .sum();
    }
    public List<BasketItem> getItems() { return items; }
    public int getTotalPrice() { return totalPrice; }
    public int getCountItems() { return countItems; }
}
