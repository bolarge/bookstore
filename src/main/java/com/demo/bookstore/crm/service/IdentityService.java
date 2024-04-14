package com.demo.bookstore.crm.service;

import com.demo.bookstore.crm.User;
import com.demo.bookstore.crm.datatransfer.SignUpRequest;
import com.demo.bookstore.crm.datatransfer.SignUpResponse;
import com.demo.bookstore.crm.datatransfer.UserRecord;
import com.demo.bookstore.utils.GenericResponse;

public interface IdentityService {
    GenericResponse<SignUpResponse>  createUserIdentity(SignUpRequest signUpRequest);
    GenericResponse<UserRecord> createUser(UserRecord userRecord, Long userIdentity);
    Iterable<User> fetchAllUsers();

}
