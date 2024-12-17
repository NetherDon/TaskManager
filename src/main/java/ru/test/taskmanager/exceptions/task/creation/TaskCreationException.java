package ru.test.taskmanager.exceptions.task.creation;

public class TaskCreationException extends IllegalArgumentException
{
    public TaskCreationException() { super(); }
    public TaskCreationException(String message) { super(message); }
    public TaskCreationException(String message, Throwable cause) { super(message, cause); } 
    public TaskCreationException(Throwable cause) { super(cause); }
}
