package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    public FixPriceProduct(UUID id, String name, int fixedPrice) {
        super(id, name, fixedPrice);
    }

    @Override
    public int getPrice() {
        return super.getPrice();
    }
}