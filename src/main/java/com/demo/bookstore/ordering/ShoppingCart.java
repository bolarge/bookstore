package com.demo.bookstore.ordering;

import com.demo.bookstore.utils.BaseEntity;
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
//@Table(name = "shopping_cart")
//@Entity
@Embeddable
@Component
public class ShoppingCart { //extends BaseEntity {
    @OneToMany
    private List<Item> items;

    public ShoppingCart(){
        this.items = new ArrayList<>();
    }

}
