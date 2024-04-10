package com.demo.bookstore.ordering;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemRequest (@NotBlank @NotNull(message = "Item name is required!") String itemName,
                           Integer quantity){
}
