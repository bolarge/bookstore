package com.demo.bookstore.crm.service;

import com.demo.bookstore.crm.User;

public interface UserContext {

    User getCurrentUser();
    void setCurrentUser(final User user);
}
