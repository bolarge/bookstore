package com.demo.bookstore.security;

import com.demo.bookstore.crm.dataaccess.IdentityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IdentityRepository identityRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var identity = identityRepository.findByUsername(username);
        var user = identity.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username)).getUser();

        return UserDetailsImpl.build(user);
    }
}
