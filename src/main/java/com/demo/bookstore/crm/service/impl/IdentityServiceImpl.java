package com.demo.bookstore.crm.service.impl;

import com.demo.bookstore.crm.*;
import com.demo.bookstore.crm.dataaccess.IdentityRepository;
import com.demo.bookstore.crm.dataaccess.RoleRepository;
import com.demo.bookstore.crm.dataaccess.UserRepository;
import com.demo.bookstore.crm.datatransfer.SignUpRequest;
import com.demo.bookstore.crm.datatransfer.SignUpResponse;
import com.demo.bookstore.crm.datatransfer.UserRecord;
import com.demo.bookstore.crm.service.IdentityService;
import com.demo.bookstore.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Slf4j
@RequiredArgsConstructor
@Service
public class IdentityServiceImpl implements IdentityService {

    private final IdentityRepository identityRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public GenericResponse<SignUpResponse> createUserIdentity(SignUpRequest signUpRequest) {
        var newIdentity = identityRepository.save(new Identity(signUpRequest.username(), encoder.encode(signUpRequest.password()),
                signUpRequest.email(), signUpRequest.dateOfBirth()));
        var requestResponse = new GenericResponse<SignUpResponse>();
        var signUPResponse = new SignUpResponse(newIdentity.getId(), newIdentity.getUsername(), newIdentity.getPassword(),
                newIdentity.getEmail(), newIdentity.getDob());
        requestResponse.setData(signUPResponse);
        requestResponse.setMessage("New User Identity successfully created");
        return  requestResponse;
    }

    @Override
    public GenericResponse<UserRecord> createUser(UserRecord userRecord, Long userIdentity) {
        Optional<Identity> queriedIdentity = identityRepository.findById(userIdentity);
        Identity foundIdentity = queriedIdentity.orElseThrow();

        Set<String> strRoles = userRecord.userRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "hr_admin":
                        Role modRole = roleRepository.findByName(UserRole.ROLE_HR_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        User newUser = new User(userRecord.firstName(),userRecord.lastName(),userRecord.isAdmin(),foundIdentity, roles,
                userRecord.isAdmin()? UserType.EMPLOYEE:UserType.CUSTOMER);
        newUser = userRepository.save(newUser);

        var userResponse = new GenericResponse<UserRecord>();

        var uRecord = new UserRecord(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.isAdmin(),
                strRoles, newUser.getIdentity().getUsername());
        userResponse.setData(uRecord);
        userResponse.setMessage(String.format("User whose UserID is %s was successfully created", userIdentity));
        return  userResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> fetchAllUsers() {
        return userRepository.findAll();
    }
}
