package ru.test.taskmanager;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ru.test.taskmanager.models.entities.User;

public class UserTests 
{
    @ParameterizedTest
    @ValueSource(strings={
        "name@mail.ru",
        "sample.test@gmail.com",
        "user@example.com",
        "user.name+tag@sub.domain.org",
        "user_name@domain.co.uk",
        "123456@domain.com",
        "user-name@domain.io"
    })
    public void shouldMatchValidEmail(String email)
    {
        assertThat(email).matches(User.EMAIL_PATTERN);
    }

    @ParameterizedTest
    @ValueSource(strings={
        "",
        "user@.com",
        "@domain.com",
        "user@domain,com",
        "user@domain@domain.com",
        "user@@domain.com",
        "userdomain.com"
    })
    public void shouldNotMatchInvalidEmail(String email)
    {
        assertThat(email).doesNotMatch(User.EMAIL_PATTERN);
    }

    @ParameterizedTest
    @ValueSource(strings={
        "12345",
        "vchsf#$$dsa-d23D",
        "K6D#%-v342#"
    })
    public void shouldMatchValidPasswords(String password)
    {
        assertThat(password).matches(User.PASSWORD_PATTERN);
    }

    @ParameterizedTest
    @ValueSource(strings={
        "",
        "1234",
        "vha 34ff#4",
        "пароль",
        "aaG42!0>",
        "+password23"
    })
    public void shouldNotMatchInvalidPasswords(String password)
    {
        assertThat(password).doesNotMatch(User.PASSWORD_PATTERN);
    }
}
