package com.demo.bookstore.crm.repository;

import com.demo.bookstore.crm.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdentityRepository identityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void testFindAll() {
        //User Identity
        var id1 = new Identity("bolarge", "P@szw0rd2$", "bolaji.salau@gmail.com");
        var id2 = new Identity("test1", "P@szw0rd2$", "test1@gmail.com");

        id1 = identityRepository.save(id1);
        id2 = identityRepository.save(id2);

        //User Roles
        var rl1 = new Role(UserRole.ROLE_USER);
        var rl2 = new Role(UserRole.ROLE_ADMIN);

        rl1 = roleRepository.save(rl1);
        rl2 = roleRepository.save(rl2);

        var userRole = new HashSet<Role>();
        userRole.add(rl1);
        //User
        var user1 = User.builder().firstName("User1").lastName("Test1").isAdmin(false).identity(id1)
                .roles(userRole).userType(UserType.CUSTOMER).build();
        var user2 = User.builder().firstName("User2").lastName("LastName").isAdmin(false).identity(id2)
                .roles(userRole).userType(UserType.CUSTOMER).build();

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        List<User> users = userRepository.findAll();
        Assertions.assertEquals(3, users.size(), "Expected 3 users in the database");
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    void testFindByIdSuccess() {
        User user = User.builder()
                .firstName("Mike")
                .lastName("Pence")
                .userType(UserType.CUSTOMER)
                .build();
        user = userRepository.save(user);
        Optional<User> user1 = userRepository.findById(1L);
        Assertions.assertTrue(user1.isPresent(), "We should find a user with ID 1");

        User u = user1.get();
        Assertions.assertEquals(1, u.getId(), "The user ID should be 1");
        Assertions.assertEquals("Pence", u.getLastName(), "Incorrect last name");
        Assertions.assertEquals(UserType.CUSTOMER, u.getUserType(), "Incorrect user type");
    }

    @Test
    void testFindByIdNotFound() {
        Optional<User> user = userRepository.findById(4L);
        Assertions.assertFalse(user.isPresent(), "A user with ID 3 should not be found");
    }

    @Test
    void testFindUsersWithLastNameLike() {
        List<User> users = userRepository.findUsersWithLastNameLike("LastName%");
        Assertions.assertEquals(1, users.size());
    }
}
