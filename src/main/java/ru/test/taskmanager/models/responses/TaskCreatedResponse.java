package ru.test.taskmanager.models.responses;

public class TaskCreatedResponse 
{
    private final String message;
    private final long taskId;

    public TaskCreatedResponse(long taskId, String message)
    {
        this.taskId = taskId;
        this.message = message;
    }

    public long getTaskId()
    {
        return this.taskId;
    }

    public String getMessage()
    {
        return this.message;
    }
}
