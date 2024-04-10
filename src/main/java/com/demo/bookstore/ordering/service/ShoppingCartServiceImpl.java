package com.demo.bookstore.ordering.service;

import com.demo.bookstore.inventory.AuthorRepository;
import com.demo.bookstore.inventory.Book;
import com.demo.bookstore.inventory.BookRepository;
import com.demo.bookstore.ordering.Item;
import com.demo.bookstore.ordering.ItemRequest;
import com.demo.bookstore.ordering.ShoppingCart;
import com.demo.bookstore.ordering.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ShoppingCart shoppingCart = new ShoppingCart();
    private BigDecimal totalAmount;

    @Override
    public ShoppingCart addItemsToCart(ItemRequest itemRequest) {
        Book book = getBook(itemRequest);
        Item anItem = new Item(book, itemRequest.quantity());
        shoppingCart.getItems().add(anItem);
        return shoppingCart;
    }

    private Book getBook(ItemRequest itemRequest) {
        Book book = bookRepository.findBookByTitle(itemRequest.itemName());
        return book;
    }

    @Override
    public List<Item> showCartItems() {
       return shoppingCart.getItems();
    }

    @Override
    public ShoppingCart removeFromCart(ItemRequest itemRequest) {
        Book book = getBook(itemRequest);
        ListIterator<Item> iterator = shoppingCart.getItems().listIterator();
        while(iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getBook().getId().equals(book.getId())) {
                this.shoppingCart.getItems().remove(item);
                break;
            }
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart updateItemQuantity(ItemRequest itemRequest) {
        Book book = getBook(itemRequest);
        ListIterator<Item> iterator = shoppingCart.getItems().listIterator();
        while (iterator.hasNext()){
            Item item = iterator.next();
            if(item.getBook().getId().equals(book.getId())){
                item.setQuantity(item.getQuantity());
                break;
            }
        }
        return shoppingCart;
    }

    @Override
    public BigDecimal getTotalAmount() {
        //return items.stream().mapToDouble(Item::getPrice).sum();
        ListIterator<Item> iterator = shoppingCart.getItems().listIterator();
        this.totalAmount = new BigDecimal(0);
        while(iterator.hasNext()) {
            Item item = iterator.next();
            this.totalAmount = this.totalAmount.add(item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return this.totalAmount;
    }
}
