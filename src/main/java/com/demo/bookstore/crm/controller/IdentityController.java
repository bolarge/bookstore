package com.demo.bookstore.crm.controller;

import com.demo.bookstore.crm.datatransfer.*;
import com.demo.bookstore.crm.repository.IdentityRepository;
import com.demo.bookstore.crm.service.IdentityService;
import com.demo.bookstore.security.UserDetailsImpl;
import com.demo.bookstore.security.jwt.JwtUtils;
import com.demo.bookstore.utils.GenericResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Tag(name = "Identities", description = "Identity Resource")
@RequestMapping("/api/v1")
@RestController
public class IdentityController {

    private final IdentityRepository identityRepository;
    private final IdentityService identityService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/identities/signup")
    public ResponseEntity<?> createAnIdentity(@Valid @RequestBody SignUpRequest signUpRequest){
        if (identityRepository.existsByUsername(signUpRequest.username())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (identityRepository.existsByEmail(signUpRequest.email())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        var requestResponse = identityService.createUserIdentity(signUpRequest);

        try {
            return ResponseEntity
                    .created(new URI("/api/v1/identities/" + requestResponse.getData().id()))
                    .eTag(Long.toString(requestResponse.getData().id()))
                    .body(requestResponse);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/identities/signin")
    public ResponseEntity<?> authenticateIdentity(@Valid @RequestBody SignInRequest signInRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.username(), signInRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/identities")
    public ResponseEntity<List<IdentityDTO>> getIdentities() {
        try {
            return ResponseEntity.ok()
                    .location((new URI("/api/v1/identities")))
                    .body(identityService.findAll());
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/identities/{id}")
    public ResponseEntity<?> getAnIdentity(@PathVariable Long id) {
        return identityService.findIdentityById(id)
                .map(identityDto -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .eTag(Long.toString(identityDto.getId()))
                                .location(new URI("/api/v1/identities/" + identityDto.getId()))
                                .body(identityDto);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
