package com.demo.bookstore.inventory;

import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    private Author author;
}
