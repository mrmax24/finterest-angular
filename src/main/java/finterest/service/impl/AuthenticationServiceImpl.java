package finterest.service.impl;

import finterest.exception.AuthenticationException;
import finterest.model.User;
import finterest.service.AuthenticationService;
import finterest.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationServiceImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String email, String login, String password) {
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        userService.add(user);
        return user;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(login);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new AuthenticationException("Incorrect username or password");
        }
        return user.get();
    }
}
