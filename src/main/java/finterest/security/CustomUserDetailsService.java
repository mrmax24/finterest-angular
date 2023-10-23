package finterest.security;

import finterest.model.User;
import finterest.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final List<GrantedAuthority> authorities
            = Collections.singletonList(new SimpleGrantedAuthority("USER"));
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(login);
        if (userOptional.isPresent()) {
            UserBuilder builder =
                    org.springframework.security.core.userdetails.User.withUsername(login);
            builder.password(userOptional.get().getPassword()).authorities(authorities);
            return builder.build();
        }
        throw new UsernameNotFoundException("User by email: " + login + " not found");
    }
}
