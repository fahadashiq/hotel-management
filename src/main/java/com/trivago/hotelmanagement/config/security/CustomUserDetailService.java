package com.trivago.hotelmanagement.config.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

       @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
           // In a proper service these user will be fetched from some user provider or a database.
           CustomUserDetail user1 = new CustomUserDetail("tenant1", "user1", "$2a$10$G4xVmyRNkgeGrOJWaqijg.YYciK..2Q7flC58sJQXpsALZ9BomINy", true, true, true, true, List.of(new SimpleGrantedAuthority("ROLE_USER")));
           CustomUserDetail user2 = new CustomUserDetail("tenant2", "user2", "$2a$10$G4xVmyRNkgeGrOJWaqijg.YYciK..2Q7flC58sJQXpsALZ9BomINy", true, true, true, true, List.of(new SimpleGrantedAuthority("ROLE_USER")));

           if (username.equals(user1.getUsername())) {
            return user1;
        }
        else if (username.equals(user2.getUsername())) {
            return user2;
        }

        throw new UsernameNotFoundException("User not found : " + username );
    }
}
