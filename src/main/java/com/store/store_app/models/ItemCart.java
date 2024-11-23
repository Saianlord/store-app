package com.store.store_app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemCart {
    private int quantity;
    private Product product;

    public ItemCart(Product product) {
        this.product = product;
    }

    public float getTotal() {
        return quantity * product.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCart itemCart = (ItemCart) o;
        return product.getId().equals(itemCart.product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getId());
    }
}
