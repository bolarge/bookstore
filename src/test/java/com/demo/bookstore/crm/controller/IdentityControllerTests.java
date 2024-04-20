package com.demo.bookstore.crm.controller;

import com.demo.bookstore.crm.*;
import com.demo.bookstore.crm.repository.IdentityRepository;
import com.demo.bookstore.crm.service.IdentityService;
import com.demo.bookstore.security.UserDetailsServiceImpl;
import com.demo.bookstore.security.jwt.JwtUtils;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = IdentityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class IdentityControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IdentityService identityService;

    @MockBean
    private IdentityRepository identityRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;
    @Mock
    private PasswordEncoder encoder;

    @Test
    @DisplayName("GET /identities success")
    void testGetIdentitiesSuccess() throws Exception {
        // Setup our mocked service
        var identity = new Identity(1L,"userName", encoder.encode("P@ssw0rd2$"), "email@gmail.com");
        var identity1 = new Identity(2L,"userName1", encoder.encode("P@ssw0rd2$"), "email1@gmail.com");
        doReturn(Lists.newArrayList(identity, identity1)).when(identityService).findAll();

        mockMvc.perform(get("/api/v1/identities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/identities"))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("userName")))
                .andExpect(jsonPath("$[0].email", is("email@gmail.com")))
                .andExpect(jsonPath("$[1].username", is("userName1")))
                .andExpect(jsonPath("$[1].email", is("email1@gmail.com")));
    }

    @Test
    @DisplayName("GET /api/v1/identities/1")
    void testGetIdentityById() throws Exception {
        Optional<Identity> identity = Optional.of(new Identity(1L, "userName", encoder.encode("P@ssw0rd2$"), "email@gmail.com"));
        doReturn(identity).when(identityService).findIdentityById(1L);

        mockMvc.perform(get("/api/v1/identities/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/identities/1"))
                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))

                .andExpect(jsonPath("$.username", is("userName")))
                .andExpect(jsonPath("$.email", is("email@gmail.com")));
    }

}
