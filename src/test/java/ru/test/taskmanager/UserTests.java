package ru.test.taskmanager;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ru.test.taskmanager.models.entities.User;

public class UserTests 
{
    @Test
    public void userEmailRegex_ShouldMatchValidEmailAdresses()
    {
        assertRegex(User.EMAIL_PATTERN, true,
            "name@mail.ru",
            "sample.test@gmail.com",
            "user@example.com",
            "user.name+tag@sub.domain.org",
            "user_name@domain.co.uk",
            "123456@domain.com",
            "user-name@domain.io"
        );

        assertRegex(User.EMAIL_PATTERN, false,
            "user@.com",
            "@domain.com",
            "user@domain,com",
            "user@domain@domain.com",
            "user@@domain.com",
            "userdomain.com"
        );
    }

    @Test
    public void userPasswordRegex_ShouldMatchValidPasswords()
    {
        assertRegex(User.PASSWORD_PATTERN, true,
            "12345",
            "vchsf#$$dsa-d23D",
            "K6D#%-v342#"
        );

        assertRegex(User.PASSWORD_PATTERN, false,
            "",
            "1234",
            "vha 34ff#4",
            "пароль",
            "aaG42!0>",
            "+password23"
        );
    }

    private static void assertRegex(Pattern regex, boolean expected, String... lines)
    {
        for (String line : lines)
        {
            assertEquals(expected, regex.matcher(line).matches(), "Input value: \"" + line + "\"");
        }
    }
}
