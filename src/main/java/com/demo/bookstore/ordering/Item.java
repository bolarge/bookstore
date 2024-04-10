package com.demo.bookstore.ordering;

import com.demo.bookstore.inventory.Book;
import com.demo.bookstore.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
public class Item extends BaseEntity {
    @ManyToOne
    private Book book;
    private int quantity;

    public Item(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Item() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return getId() != null && Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
