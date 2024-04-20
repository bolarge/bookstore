package com.demo.bookstore.crm.datatransfer;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class IdentityDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Date dob;
}
