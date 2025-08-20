package com.example.manageskill.service;

import com.example.manageskill.model.User;
import com.example.manageskill.repository.UserRepository;
import com.example.manageskill.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        // Accept either username OR email
        User user = null;

        // If you added findByUsernameOrEmail in the repository, this line will work:
        try {
            user = userRepository.findByUsernameOrEmail(input);
        } catch (Exception ignore) {
            // method may not exist; fall back to the two-step lookup below
        }

        if (user == null) {
            user = userRepository.findByUsername(input);
        }
        if (user == null) {
            user = userRepository.findByEmail(input);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User \"" + input + "\" không tồn tại trong database");
        }

        // Always use the canonical username for roles & Spring Security principal
        String canonicalUsername = user.getUsername();

        List<String> roles = userRoleRepository.findAllRoleByUser(canonicalUsername);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }

        return new org.springframework.security.core.userdetails.User(
                canonicalUsername,           // principal username
                user.getPassword(),          // hashed password
                grantedAuthorities
        );
    }
}
