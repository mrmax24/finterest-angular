package finterest.dto.request;

import finterest.lib.ValidEmail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRequestDto {

    @NotBlank(message = "Login is required")
    private String login;
    @NotBlank(message = "Email is required")
    @ValidEmail
    private String email;
    @Size(min = 8, max = 40, message = "Password must be at least 8 characters long")
    private String password;

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
