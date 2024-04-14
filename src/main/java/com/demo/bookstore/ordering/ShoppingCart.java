package com.demo.bookstore.ordering;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
@Component
public class ShoppingCart {
    @OneToMany
    private List<Item> items;
    public ShoppingCart(){
        this.items = new ArrayList<>();
    }
}
