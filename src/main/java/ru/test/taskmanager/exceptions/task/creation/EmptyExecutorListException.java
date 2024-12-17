package ru.test.taskmanager.exceptions.task.creation;

public class EmptyExecutorListException extends TaskCreationException
{
    public EmptyExecutorListException()
    {
        super("The task must have at least one executor");
    }

    public EmptyExecutorListException(String message)
    {
        super(message);
    }
}
