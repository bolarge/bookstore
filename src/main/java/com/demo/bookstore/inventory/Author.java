package com.demo.bookstore.inventory;

import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "authors")
public class Author extends BaseEntity {

    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String email;
    private String bio;
    private String websiteUrl;

    public Author(String name, String email, String bio) {
        this.name = name;
        this.email = email;
        this.bio = bio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        if (!super.equals(o)) return false;
        return getName().equals(author.getName()) && getEmail().equals(author.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getEmail());
    }
}
