package com.demo.bookstore.ordering.repository;

import com.demo.bookstore.crm.User;
import com.demo.bookstore.inventory.Book;
import com.demo.bookstore.ordering.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findItemByBook(Book book);
    List<Item> findItemByCustomer(Optional<User> user);
}
