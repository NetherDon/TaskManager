package ru.test.taskmanager.exceptions.task;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnavailableTaskException extends IllegalArgumentException
{
    public UnavailableTaskException(String message) { super(message); }
    public UnavailableTaskException(String message, Throwable cause) { super(message, cause); }
}
