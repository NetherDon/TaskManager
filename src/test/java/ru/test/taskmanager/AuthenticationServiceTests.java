package ru.test.taskmanager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import ru.test.taskmanager.exceptions.auth.EmailDuplicateException;
import ru.test.taskmanager.models.entities.JwtDetails;
import ru.test.taskmanager.models.entities.User;
import ru.test.taskmanager.repositories.JwtRepository;
import ru.test.taskmanager.repositories.UserRepository;
import ru.test.taskmanager.repositories.UserRoleRepository;
import ru.test.taskmanager.services.AuthenticationService;
import ru.test.taskmanager.utils.JwtUtils;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests 
{
    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository users;

    @Mock
    private JwtRepository authTokens;

    @Mock
    private UserRoleRepository roles;

    @InjectMocks
    private AuthenticationService service;

    @BeforeEach
    public void setup()
    {
        List<User> userDetails  = List.of(
            new User("test@gmail.com", "123"),
            new User("admin@a.ru", "pass54")
        );

        for (User user : userDetails)
        {
            Mockito.lenient().when(this.users.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        }
    }

    @DisplayName("Test login() method")
    @Test
    public void shouldReturnToken_whenUserExists()
    {
        JwtDetails jwt1 = assertDoesNotThrow(() -> this.service.login("test@gmail.com", "123"));
        verify(this.authTokens, times(1)).save(jwt1);

        JwtDetails jwt2 = assertDoesNotThrow(() -> this.service.login("admin@a.ru", "pass54"));
        verify(this.authTokens, times(1)).save(jwt2);
        
        assertThatThrownBy(() -> this.service.login("test@gmail.com", "1243"))
            .isExactlyInstanceOf(BadCredentialsException.class)
            .hasMessage("Incorrect password");

        assertThatThrownBy(() -> this.service.login("test@mail.com", "123"))
            .isExactlyInstanceOf(BadCredentialsException.class)
            .hasMessage("The user with e-mail \"test@mail.com\" does not exist");
    }


    @DisplayName("Test registerUser() method")
    @Test
    public void shouldReturnUser_whenUserRegister()
    {
        User newUser = new User("new@mail.ru", "123");
        assertDoesNotThrow(() -> this.service.registerUser(newUser));
        
        verify(this.users, times(1)).save(newUser);

        assertThatThrownBy(() -> this.service.registerUser(new User("test@gmail.com", "123")))
            .isExactlyInstanceOf(EmailDuplicateException.class);
    }
}
