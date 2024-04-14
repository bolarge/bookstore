package com.demo.bookstore.crm.datatransfer;

import java.util.Date;

public record SignUpResponse(Long id,
                             String userName,
                             String password,
                             String email,
                             Date dob) {
}
