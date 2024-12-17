package ru.test.taskmanager.exceptions.auth;

import org.springframework.security.core.AuthenticationException;

public class RegistrationException extends AuthenticationException
{
    public RegistrationException(String message)
    {
        super(message);
    }

    public RegistrationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
