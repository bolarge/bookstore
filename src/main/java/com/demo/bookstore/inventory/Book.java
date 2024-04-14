package com.demo.bookstore.inventory;

import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    @Column(unique = true)
    private String title;
    @Column(unique = true)
    private String isbn;
    private String publicationYear;
    @Enumerated(EnumType.STRING)
    private BookGenre bookGenre;
    private BigDecimal price;

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    public Book(String title, String isbn, String publicationYear, BookGenre valueOf, Author bookAuthor, BigDecimal price) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.bookGenre = valueOf;
        this.author = bookAuthor;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        if (!super.equals(o)) return false;
        return getTitle().equals(book.getTitle()) && getIsbn().equals(book.getIsbn()) && getAuthor().equals(book.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTitle(), getIsbn(), getAuthor());
    }
}
