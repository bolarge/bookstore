package com.demo.bookstore.crm.service.impl;

import com.demo.bookstore.crm.*;
import com.demo.bookstore.crm.repository.IdentityRepository;
import com.demo.bookstore.crm.repository.RoleRepository;
import com.demo.bookstore.crm.repository.UserRepository;
import com.demo.bookstore.crm.datatransfer.SignUpRequest;
import com.demo.bookstore.crm.datatransfer.SignUpResponse;
import com.demo.bookstore.crm.datatransfer.UserRecord;
import com.demo.bookstore.crm.service.IdentityService;
import com.demo.bookstore.utils.GenericListResponse;
import com.demo.bookstore.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
            Role userRole = roleRepository.findByUserRole(UserRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByUserRole(UserRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "hr_admin":
                        Role modRole = roleRepository.findByUserRole(UserRole.ROLE_HR_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByUserRole(UserRole.ROLE_USER)
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
    public GenericListResponse<SignUpResponse> getAllIdentities(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Identity> identities = identityRepository.findAll(pageable);
        List<Identity> listOfIdentities = identities.getContent();
        List<SignUpResponse> content = listOfIdentities.stream().map(this::mapToSignUpResponse).collect(Collectors.toList());

        GenericListResponse<SignUpResponse> identitiesList = new GenericListResponse<>();
        identitiesList.setContent(content);
        identitiesList.setPageNo(identities.getNumber());
        identitiesList.setPageSize(identities.getSize());
        identitiesList.setTotalElements(identities.getTotalElements());
        identitiesList.setTotalPages(identities.getTotalPages());
        identitiesList.setLast(identities.isLast());

        return identitiesList;
    }

    private SignUpResponse mapToSignUpResponse(Identity signUpResponse) {
        SignUpResponse response = new SignUpResponse(signUpResponse.getId(), signUpResponse.getUsername(),
                signUpResponse.getPassword(), signUpResponse.getEmail(), signUpResponse.getDob());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> fetchAllUsers() {
        return userRepository.findAll();
    }


}
