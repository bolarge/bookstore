package com.demo.bookstore.crm.service;

import com.demo.bookstore.crm.*;
import com.demo.bookstore.crm.datatransfer.SignUpRequest;
import com.demo.bookstore.crm.datatransfer.SignUpResponse;
import com.demo.bookstore.crm.datatransfer.UserRecord;
import com.demo.bookstore.crm.repository.IdentityRepository;
import com.demo.bookstore.crm.repository.RoleRepository;
import com.demo.bookstore.crm.repository.UserRepository;
import com.demo.bookstore.crm.service.impl.IdentityServiceImpl;
import com.demo.bookstore.utils.GenericListResponse;
import com.demo.bookstore.utils.GenericResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IdentityServiceTest {

    @Mock
    private IdentityRepository identityRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private IdentityServiceImpl identityService;
    @Mock
    private PasswordEncoder encoder;

    @DisplayName("Tests createUserIdentity")
    @Test
    public void identityService_CreateIdentity_ReturnsSignUpResponse(){
        var identity1 = new Identity(1L,"bigdaddy", "P@szw0rd2$", "bigdaddy@gmail.com");

        var identity2 = new SignUpRequest("userName", encoder.encode("P@ssw0rd2$"), "P@ssw0rd2$",
                "userName@gmail.com", new Date());
        when(identityRepository.save(Mockito.any(Identity.class))).thenReturn(identity1);
        GenericResponse<SignUpResponse>  createdIdentityResponse = identityService.createUserIdentity(identity2);

        Assertions.assertThat(createdIdentityResponse).isNotNull();
    }

    @DisplayName("Tests pageable getAllIdentities")
    @Test
    public void identityService_GetAllIdentities_ReturnsResponseDto() {
        Page<Identity> identities = Mockito.mock(Page.class);

        when(identityRepository.findAll(Mockito.any(Pageable.class))).thenReturn(identities);
        GenericListResponse<SignUpResponse> allIdentities = identityService.getAllIdentities(1,10);

        Assertions.assertThat(allIdentities).isNotNull();
    }
    @DisplayName("Tests createUser")
    @Test
    public void identityService_CreateUser(){
        //User Identity
        var identityId = 1L;
        var identity = new Identity(identityId,"userName", encoder.encode("P@ssw0rd2$"),
                "P@ssw0rd2$");
        when(identityRepository.findById(identityId)).thenReturn(Optional.of(identity));
        Optional<Identity> createdIdentityResponse = identityService.findIdentityById(identityId);

        //User Roles
        var roleId = 1L;
        var role = new Role(roleId, UserRole.ROLE_USER);
        when(roleRepository.findByUserRole(UserRole.ROLE_USER)).thenReturn(Optional.of(role));

        Set<Role> userRole = new HashSet<>();
        userRole.add(role);

        Set<String> userRole1 = new HashSet<>();
        userRole1.add(String.valueOf(role));

        //User
        var userID = 1L;
        var user = new User("firstName", "lastName", false, identity,
                userRole, UserType.CUSTOMER);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        var userRecord = new UserRecord(userID,"firstName", "lastName", false,
                userRole1, identity.getUsername());

        GenericResponse<UserRecord>  createdUserResponse = identityService.createUser(userRecord, identityId);
        Assertions.assertThat(createdUserResponse).isNotNull();
    }
}
