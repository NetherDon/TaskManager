package ru.test.taskmanager.models.responses;

import ru.test.taskmanager.models.properties.FailType;

public class FailInfo 
{
    private final FailType error;
    private final String message;

    public FailInfo(FailType error, String message) 
    {
        this.error = error;
        this.message = message;
    }

    public int getCode()
    {
        return this.error.getCode();
    }

    public String getName()
    {
        return this.error.getName();
    }

    public String getMessage()
    {
        return this.message;
    }
}
