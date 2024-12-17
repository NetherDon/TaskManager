package ru.test.taskmanager.exceptions.auth;

public class EmailDuplicateException extends RegistrationException
{
    private final String email;

    public EmailDuplicateException(String email, String message) 
    {
        super(message);
        this.email = email;
    }

    public EmailDuplicateException(String email, String message, Throwable cause) 
    {
        super(message, cause);
        this.email = email;
    }

    public String getEmail()
    {
        return this.email;
    }
    
}
