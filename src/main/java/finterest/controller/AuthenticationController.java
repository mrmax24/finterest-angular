package finterest.controller;

import finterest.dto.request.UserLoginDto;
import finterest.dto.request.UserRequestDto;
import finterest.exception.AuthenticationException;
import finterest.model.User;
import finterest.security.jwt.JwtTokenProvider;
import finterest.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(AuthenticationService authenticationService,
                                    AuthenticationManager authenticationManager,
                                    UserDetailsService userDetailsService,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserRequestDto requestDto) {
        try {
            User user = authenticationService.register(requestDto.getEmail(),
                    requestDto.getLogin(), requestDto.getPassword());
            String token = jwtTokenProvider.createToken(user.getLogin(), user.getEmail());
            return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Registration failed", "message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto)
            throws AuthenticationException {
        try {
            User user = authenticationService.login(userLoginDto.getLogin(),
                    userLoginDto.getPassword());
            String token = jwtTokenProvider.createToken(user.getEmail(), user.getLogin());
            System.out.println(token);

            return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication failed", "message", e.getMessage()));
        }
    }
}
