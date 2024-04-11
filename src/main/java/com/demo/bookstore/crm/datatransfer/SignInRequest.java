package com.demo.bookstore.crm.datatransfer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(@NotBlank @Size(min = 3, max = 20) String username,
                             @NotBlank @Size(min = 8, max = 20)String password)  {
}
