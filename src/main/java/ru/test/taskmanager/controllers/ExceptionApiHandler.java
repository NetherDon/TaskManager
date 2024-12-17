package ru.test.taskmanager.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import ru.test.taskmanager.exceptions.auth.EmailDuplicateException;
import ru.test.taskmanager.exceptions.auth.RegistrationException;
import ru.test.taskmanager.exceptions.task.TaskNotFoundException;
import ru.test.taskmanager.exceptions.task.creation.EmptyExecutorListException;
import ru.test.taskmanager.exceptions.task.creation.TaskExecutorNotFoundException;
import ru.test.taskmanager.exceptions.task.status.UnavailableTaskStatusException;
import ru.test.taskmanager.models.properties.FailType;
import ru.test.taskmanager.models.responses.FailInfo;
import ru.test.taskmanager.models.responses.FailResponse;
import ru.test.taskmanager.models.responses.InvalidValueFailInfo;

@RestControllerAdvice
public class ExceptionApiHandler 
{
    @ExceptionHandler(EmailDuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailResponse emailDuplicateException(EmailDuplicateException exception)
    {
        return FailResponse.of(
            new FailInfo(FailType.SAME_EMAIL, exception.getMessage())
        );
    }

    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailResponse registrationException(RegistrationException exception)
    {
        return FailResponse.of(
            new FailInfo(FailType.UNKNOWN, exception.getMessage())
        );
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailResponse taskNotFoundException(TaskNotFoundException exception)
    {
        return FailResponse.of(
            new FailInfo(FailType.TASK_NOT_FOUND, exception.getMessage())
        );
    }

    @ExceptionHandler(UnavailableTaskStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailResponse unavailableTaskStatusException(UnavailableTaskStatusException exception)
    {
        return FailResponse.of(
            new FailInfo(FailType.UNABAILABLE_TASK_STATUS, exception.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailResponse typeMismatchException(MethodArgumentTypeMismatchException exception)
    {
        return FailResponse.of(
            new InvalidValueFailInfo(exception.getPropertyName())
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailResponse missingParameterException(MissingServletRequestParameterException exception)
    {
        return FailResponse.of(
            new InvalidValueFailInfo(exception.getParameterName())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @SuppressWarnings("null")
    public FailResponse argumentNotValidException(MethodArgumentNotValidException exception)
    {
        List<FailInfo> fields = exception.getBindingResult().getAllErrors().stream()
            .map((error) -> 
            {
                if (error instanceof FieldError fieldError)
                {
                    return new InvalidValueFailInfo(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                    );
                }

                String message = error.getDefaultMessage();
                if (message == null)
                {
                    message = "An unknown error occurred";
                }
                return new FailInfo(FailType.UNKNOWN, message);
            })
            .toList();

        return FailResponse.of(fields);
    }

    @ExceptionHandler(EmptyExecutorListException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailResponse emptyExecutorListException(EmptyExecutorListException exception)
    {
        return FailResponse.of(
            new FailInfo(FailType.EMPTY_EXECUTOR_LIST, exception.getMessage())
        );
    }

    @ExceptionHandler(TaskExecutorNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailResponse executorNotFoundException(TaskExecutorNotFoundException exception)
    {
        return FailResponse.of(
            new FailInfo(FailType.EXECUTOR_NOT_FOUND, exception.getMessage())
        );
    }
}
