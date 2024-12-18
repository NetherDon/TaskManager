package ru.test.taskmanager.exceptions.task.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.test.taskmanager.models.properties.TaskStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnavailableTaskStatusException extends TaskStatusChangeException
{
    private final TaskStatus status;

    public UnavailableTaskStatusException(TaskStatus status)
    {
        super("The status '" + status + "' cannot be set by this user");
        this.status = status;
    }

    public UnavailableTaskStatusException(TaskStatus status, String message)
    {
        super(message);
        this.status = status;
    }
    
    public TaskStatus getStatus()
    {
        return this.status;
    }
    
}
