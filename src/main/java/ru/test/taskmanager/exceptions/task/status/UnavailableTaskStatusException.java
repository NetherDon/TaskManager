package ru.test.taskmanager.exceptions.task.status;

import ru.test.taskmanager.models.properties.TaskStatus;

public class UnavailableTaskStatusException extends TaskStatusChangeException
{
    private final TaskStatus status;

    public UnavailableTaskStatusException(TaskStatus status)
    {
        super("The status \"" + status + "\" cannot be set by this user");
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
