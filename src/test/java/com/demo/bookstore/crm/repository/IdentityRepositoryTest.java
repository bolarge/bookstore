package com.demo.bookstore.crm.repository;

import com.demo.bookstore.crm.Identity;
import com.demo.bookstore.crm.User;
import com.demo.bookstore.crm.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class IdentityRepositoryTest {

    @Autowired
    private IdentityRepository identityRepository;

    @Test
    @DisplayName("Test find all Identities")
    public void IdentityRepository_GetAll() {
        var id1 = new Identity("bolarge", "P@szw0rd2$", "bolaji.salau@gmail.com");
        var id2 = new Identity("test1", "P@szw0rd2$", "test1@gmail.com");

        identityRepository.save(id1);
        identityRepository.save(id2);

        List<Identity> ids = identityRepository.findAll();
        Assertions.assertEquals(2, ids.size(), "Expected 2 identities in the database");
    }
}
