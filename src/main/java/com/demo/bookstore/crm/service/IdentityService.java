package com.demo.bookstore.crm.service;

import com.demo.bookstore.crm.Identity;
import com.demo.bookstore.crm.User;
import com.demo.bookstore.crm.datatransfer.SignUpRequest;
import com.demo.bookstore.crm.datatransfer.UserRecord;

public interface IdentityService {
    Identity signUpUser(SignUpRequest signUpRequest);
    UserRecord createUserProfile(UserRecord userRecord, Long userIdentity);
    Iterable<User> fetchAllUsers();

}
