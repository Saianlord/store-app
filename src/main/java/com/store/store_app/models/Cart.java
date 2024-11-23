package com.store.store_app.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private List<ItemCart> items = new ArrayList<>();

    public void addItemCart(ItemCart itemCart) {
        Optional<ItemCart> existingItem = items.stream()
                .filter(i -> i.equals(itemCart))
                .findFirst();
        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + 1);
        } else {
            itemCart.setQuantity(1);
            items.add(itemCart);
        }
    }

    public List<ItemCart> getItems() {
        return items;
    }

    public float getTotal() {
        return (float) items.stream()
                .mapToDouble(ItemCart::getTotal)
                .sum(); }

    public void updateQuantity(Long productId, int quantity) {
        items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(i -> i.setQuantity(quantity));
    }

    public void removeProduct(Long productId) {
        items.removeIf(i -> i.getProduct().getId().equals(productId));
    }

    public void clearCart(){
        items.clear();
    }
}