package com.demo.bookstore.crm;

import com.demo.bookstore.crm.dataaccess.UserRepository;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@ExtendWith(DBUnitExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserRepository userRepository;

    public ConnectionHolder getConnectionHolder() {
        return () -> dataSource.getConnection();
    }

    @Test
    @DataSet("users.yml")
    void testFindAll() {
        List<User> users = Lists.newArrayList(userRepository.findAll());
        Assertions.assertEquals(4, users.size(), "Expected 4 users in the database");
    }

    @Test
    @DataSet("users.yml")
    void testFindByIdSuccess() {
        Optional<User> user = userRepository.findById(1L);
        Assertions.assertTrue(user.isPresent(), "We should find a user with ID 1");

        User u = user.get();
        Assertions.assertEquals(1, u.getId(), "The user ID should be 1");
        Assertions.assertEquals("User 1", u.getLastName(), "Incorrect last name");
        Assertions.assertEquals("This is user 1", u.getUserType(), "Incorrect user type");
    }

    @Test
    @DataSet("users.yml")
    void testFindByIdNotFound() {
        Optional<User> user = userRepository.findById(3L);
        Assertions.assertFalse(user.isPresent(), "A user with ID 3 should not be found");
    }

    @Test
    @DataSet("users.yml")
    void testFindUsersWithLastNameLike() {
        List<User> users = userRepository.findUsersWithLastNameLike("LastName%");
        Assertions.assertEquals(2, users.size());
    }
}
