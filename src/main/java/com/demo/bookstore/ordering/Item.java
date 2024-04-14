package com.demo.bookstore.ordering;

import com.demo.bookstore.crm.User;
import com.demo.bookstore.inventory.Book;
import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "item")
public class Item extends BaseEntity {
    @JsonBackReference
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    private User customer;
    @ManyToOne
    private Book book;
    private int quantity;

    public Item(Book book, int quantity, User user) {
        this.book = book;
        this.quantity = quantity;
        this.customer = user;
    }
    public Item() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        if (!super.equals(o)) return false;
        return getCustomer().equals(item.getCustomer()) && getBook().equals(item.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomer(), getBook());
    }
}
