package com.demo.bookstore.ordering.service;

import com.demo.bookstore.crm.Identity;
import com.demo.bookstore.crm.User;
import com.demo.bookstore.crm.repository.IdentityRepository;
import com.demo.bookstore.inventory.Book;
import com.demo.bookstore.inventory.repository.BookRepository;
import com.demo.bookstore.ordering.*;
import com.demo.bookstore.ordering.repository.ItemRepository;
import com.demo.bookstore.ordering.repository.SalesOrderRepository;
import com.demo.bookstore.payment.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final IdentityRepository identityRepository;
    private final BookRepository bookRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final ItemRepository itemRepository;
    private final ShoppingCart shoppingCart; // = new ShoppingCart();
    private final PaymentRepository paymentRepository;
    private final List<Item> itemList;
    @Autowired
    private PaymentClient paymentClient;

    @Override
    public ShoppingCart addItemsToCart(ItemRequest itemRequest) {
        User customer = null;
        Optional<User> foundUser = getAuthenticatedUser();
        if(foundUser.isPresent()){
            customer = foundUser.get().getIdentity().getUser();
        }
        shoppingCart.setItems(itemList);
        Book book = getBook(itemRequest);
        Item anItem = new Item(book, itemRequest.quantity(), customer);
        anItem = itemRepository.save(anItem);
        shoppingCart.getItems().add(anItem);
        return shoppingCart;
    }

    @Override
    public List<Item> showCartItems() {
        Optional<User> foundUser = getAuthenticatedUser();
       if(shoppingCart.getItems() == null){
           List<Item> items = itemRepository.findItemByCustomer(foundUser);
           if(items.size() > 0){
               //shoppingCart.getItems().addAll(items);
               shoppingCart.setItems(items);
           }
       }
        return shoppingCart.getItems();
    }

    @Override
    public ShoppingCart removeFromCart(ItemRequest itemRequest) {
        Item foundItem = getAnItemByBook(itemRequest);
        for (Item item : shoppingCart.getItems()) {
            if (item.getBook().getId().equals(foundItem.getBook().getId())) {
                this.shoppingCart.getItems().remove(item);
                itemRepository.delete(item);
                break;
            }
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart updateItemQuantity(ItemRequest itemRequest) {
        Book book = getBook(itemRequest);
        for (Item item : shoppingCart.getItems()) {
            if (item.getBook().getId().equals(book.getId())) {
                item.setQuantity(item.getQuantity());
                break;
            }
        }
        return shoppingCart;
    }

    @Override
    public BigDecimal getTotalAmount() {
        ListIterator<Item> iterator = shoppingCart.getItems().listIterator();
        BigDecimal totalAmount = new BigDecimal(0);
        while(iterator.hasNext()) {
            Item item = iterator.next();
            totalAmount = totalAmount.add(item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return totalAmount;
    }

    @Override
    public SalesOrder checkOutShoppingCart() {
        User customer = null;
        Optional<User> foundUser = getAuthenticatedUser();
        if (foundUser.isPresent()){
            customer = foundUser.get().getIdentity().getUser();
        }
        BigDecimal totalAmount = getTotalAmount();
        SalesOrder salesOrder = new SalesOrder(customer, OrderStatus.CHECKOUT, totalAmount, shoppingCart, PaymentStatus.PROCESSING,
                null, String.valueOf(System.currentTimeMillis()));
        salesOrder = salesOrderRepository.save(salesOrder);

        Payment newPayment = null;
        if(newPayment == null){
            newPayment = paymentRepository.save(new Payment("", salesOrder.getOrderAmount().toString(), "", PaymentChannel.TRANSFER,
                    salesOrder));
        }
        salesOrder.setPayment(newPayment);

        PaymentResponse paymentResponse = makePayment(salesOrder);

        return salesOrder;
    }

    private PaymentResponse makePayment(SalesOrder salesOrder) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(salesOrder.getOrderAmount().toString());
        PaymentResponse paymentResponse = paymentClient.transfer(paymentRequest);
        log.info(String.valueOf(paymentResponse));
        return paymentResponse;
    }

    private Book getBook(ItemRequest itemRequest) {
        return bookRepository.findByTitle(itemRequest.itemName());
    }

    private Item getAnItemByBook(ItemRequest itemRequest) {
        var foundBook = getBook(itemRequest);
        return itemRepository.findItemByBook(foundBook);
    }

    private Optional<User> getAuthenticatedUser(){
        String currentUserName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        Optional<Identity> foundIdentity = identityRepository.findByUsername(currentUserName);
        return foundIdentity.map(Identity::getUser);
    }
}
