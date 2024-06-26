package com.demo.bookstore.crm.datatransfer;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UserRecord(Long id,
                        @NotEmpty(message = "validation required (not empty, min 3 characters)")
                         String firstName,
                         @NotEmpty(message = "validation required (not empty, min 3 characters)")
                         String lastName,
                         boolean isAdmin,
                         Set<String> userRoles,
                         String username) {
}
