package com.mvii3iv.sat.security;

import com.mvii3iv.sat.components.user.UserRepository;
import com.mvii3iv.sat.components.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MongoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findById(username);

        if(user == null) {
            throw new UsernameNotFoundException("Users not found");
        }

        String role = "ROLE_ADMIN";

        if(!user.getRole().equals("ROLE_ADMIN"))
            role = "ROLE_USER";

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role));
        return new User(user.getId(), user.getPass(), authorities);
    }
}