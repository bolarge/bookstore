package com.demo.bookstore.ordering.service;

import com.demo.bookstore.ordering.Item;
import com.demo.bookstore.ordering.ItemRequest;
import com.demo.bookstore.ordering.SalesOrder;
import com.demo.bookstore.ordering.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartService {
    //Shopping cart
    ShoppingCart addItemsToCart(ItemRequest itemRequest);
    List<Item> showCartItems();
    ShoppingCart removeFromCart(ItemRequest itemRequest);
    ShoppingCart updateItemQuantity(ItemRequest itemRequest);
    BigDecimal getTotalAmount();
    SalesOrder checkOutShoppingCart();

    //Item getAnItemByBook(ItemRequest itemRequest);
}
