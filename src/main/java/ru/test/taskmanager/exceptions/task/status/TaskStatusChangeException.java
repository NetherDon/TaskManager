package ru.test.taskmanager.exceptions.task.status;

public class TaskStatusChangeException extends IllegalArgumentException
{
    public TaskStatusChangeException() { super(); }
    public TaskStatusChangeException(String message) { super(message); }
    public TaskStatusChangeException(String message, Throwable cause) { super(message, cause); } 
    public TaskStatusChangeException(Throwable cause) { super(cause); }
}
