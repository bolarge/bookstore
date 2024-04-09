package com.demo.bookstore.inventory;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRecord(@NotBlank @NotNull(message = "Book title is required!") String title,
                         @NotBlank @NotNull @Size(min = 11, max = 11, message = "ISBN is required!") String isbn,
                         @NotBlank @NotNull(message = "Publication year is required") String publicationYear,
                         @NotBlank @NotNull (message = "Genre is required!") String genre,
                         Integer quantity,
                         String author,
                         String purchaseOrderId,
                         Long id,
                         String itemDescription) {
}
