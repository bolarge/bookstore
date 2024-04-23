package com.demo.bookstore.crm.controller;

import com.demo.bookstore.crm.*;
import com.demo.bookstore.crm.datatransfer.SignUpRequest;
import com.demo.bookstore.crm.datatransfer.SignUpResponse;
import com.demo.bookstore.crm.repository.IdentityRepository;
import com.demo.bookstore.crm.service.IdentityService;
import com.demo.bookstore.security.UserDetailsServiceImpl;
import com.demo.bookstore.security.jwt.JwtUtils;
import com.demo.bookstore.utils.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        Identity identity = new Identity(1L, "userName", encoder.encode("P@zzw0rd24$"), "email@gmail.com");
        //doReturn(identity).when(identityService).findIdentityById(1L);
        when(identityService.findIdentityById(1L)).thenReturn(Optional.of(identity));

        mockMvc.perform(get("/api/v1/identities/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/identities/1"))
                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))

                .andExpect(jsonPath("$.username", is("userName")))
                .andExpect(jsonPath("$.email", is("email@gmail.com")));
    }

    @Test
    @DisplayName("POST /api/v1/identities/signup")
    void testCreateUserIdentity() throws Exception {
        // Setup our mocked service
        String pattern = "dd-mm-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("12-01-1988");
        var identityToPost = new SignUpRequest("userName", "P@ssw0rd2$", "P@ssw0rd2$","email@gmail.com", date);
        var identityToReturn = new SignUpResponse(1L,"userName", "P@ssw0rd2$", "email@gmail.com", date);
        var requestResponse = new GenericResponse<SignUpResponse>();
        requestResponse.setData(identityToReturn);
        requestResponse.setMessage("Identity created successfully");
        doReturn(requestResponse).when(identityService).createUserIdentity(any());

        // Execute the POST request
        mockMvc.perform(post("/api/v1/identities/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(identityToPost)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/identities/signup/1"))
                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))

                // Validate the returned fields
                //.andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.data.userName", is("userName")))
                .andExpect(jsonPath("$.data.email", is("email@gmail.com")));
    }

/*

    @Test
    @DisplayName("PUT /api/v1/identities/1")
    void testUpdateAnIdentity() throws Exception {
        String pattern = "dd-mm-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("12-01-1988");
        var identityToPut = new SignUpRequest("userName", "P@ssw0rd2$", "P@ssw0rd2$","email@gmail.com", date);
        var identityToReturnFindBy = Optional.of(new Identity(1L, "userName", encoder.encode("P@ssw0rd2$"), "email@gmail.com"));
        var identityToReturnSave = new SignUpResponse(1L,"userName", "P@ssw0rd2$", "email1@gmail.com", date);
        var requestResponse = new GenericResponse<SignUpResponse>();
        requestResponse.setData(identityToReturnSave);
        requestResponse.setMessage("Identity updated successfully");


        when(identityService.findIdentityById(1L)).thenReturn(identityToReturnFindBy);
        when(identityService.createUserIdentity(any())).thenReturn(requestResponse);

        // Execute the POST request
        mockMvc.perform(put("/api/v1/identities/{id}", 1l)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.IF_MATCH, 2)
                        .content(asJsonString(identityToPut)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/identities/1"))
                .andExpect(header().string(HttpHeaders.ETAG, "\"email1@gmail.com\""))

                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.userName", is("UserName")))
                .andExpect(jsonPath("$.data.email", is("email1@gmail.com")));
    }

*/

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
