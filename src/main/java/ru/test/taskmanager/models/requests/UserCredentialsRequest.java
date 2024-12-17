package ru.test.taskmanager.models.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.test.taskmanager.models.entities.User;

public class UserCredentialsRequest 
{
    @NotNull
    @Pattern(regexp = User.EMAIL_REGEX)
    private String email;

    @NotNull
    @Pattern(regexp = User.PASSWORD_REGEX)
    @Size(min = User.MIN_PASSWORD_SIZE)
    private String password;
    
    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String username)
    {
        this.email = username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
