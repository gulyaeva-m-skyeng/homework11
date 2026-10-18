package org.skypro.skyshop.model.basket;

import org.skypro.skyshop.model.product.Product;

public class BasketItem {
    private final String name;
    private final int price;
    private final int quantity;
    private final int total;

    public BasketItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = quantity;
        // total вычисляется в конструкторе, как требовалось
        this.total = price * quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "BasketItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}