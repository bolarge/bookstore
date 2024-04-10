package com.demo.bookstore.ordering.service;

import com.demo.bookstore.ordering.Item;
import com.demo.bookstore.ordering.ItemRequest;
import com.demo.bookstore.ordering.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartService {

    ShoppingCart addItemsToCart(ItemRequest itemRequest);
    List<Item> showCartItems();
    ShoppingCart removeFromCart(ItemRequest itemRequest);

    ShoppingCart updateItemQuantity(ItemRequest itemRequest);
    BigDecimal getTotalAmount();


}
