package finterest.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class UserLoginDto {
    @NotBlank(message = "Login can't be null or blank!")
    private String login;
    @NotBlank(message = "Password can't be null or blank!")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLoginDto that = (UserLoginDto) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return "UserLoginDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}