package ru.test.taskmanager.exceptions.task.creation;

public class TaskExecutorNotFoundException extends TaskCreationException
{
    public TaskExecutorNotFoundException() { super("Executor not found"); }
    public TaskExecutorNotFoundException(String message) { super(message); }
}
