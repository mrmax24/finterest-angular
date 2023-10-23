package finterest.service;

import finterest.exception.AuthenticationException;
import finterest.model.User;

public interface AuthenticationService {
    User register(String email, String login, String password);

    User login(String login, String password) throws AuthenticationException;
}
