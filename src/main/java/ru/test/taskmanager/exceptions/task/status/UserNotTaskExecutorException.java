package ru.test.taskmanager.exceptions.task.status;

public class UserNotTaskExecutorException extends TaskStatusChangeException
{
    public UserNotTaskExecutorException()
    {
        super("The user is not an executor for this task");
    }

    public UserNotTaskExecutorException(String message)
    {
        super(message);
    }
}
