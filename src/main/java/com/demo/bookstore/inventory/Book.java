package com.demo.bookstore.inventory;

import com.demo.bookstore.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    private String title;
    private String isbn;
    private Date publicationYear;
    @Enumerated(EnumType.STRING)
    private BookGenre bookGenre;

    @ManyToOne
    private Author author;
}
