package ru.test.taskmanager.exceptions.task;

public class TaskNotFoundException extends IllegalArgumentException
{
    public TaskNotFoundException(String message) { super(message); }
    public TaskNotFoundException(String message, Throwable cause) { super(message, cause); }
}
