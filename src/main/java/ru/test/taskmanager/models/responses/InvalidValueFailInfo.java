package ru.test.taskmanager.models.responses;

import ru.test.taskmanager.models.properties.FailType;

public class InvalidValueFailInfo extends FailInfo
{
    private final String propertyName;
    
    public InvalidValueFailInfo(FailType error, String propertyName, String message) 
    {
        super(error, message);
        this.propertyName = propertyName;
    }

    public InvalidValueFailInfo(FailType error, String propertyName) 
    {
        this(error, propertyName, "Invalid value for property '" + propertyName + "'");
    }

    public InvalidValueFailInfo(String propertyName, String message) 
    {
        this(FailType.INVALID_ARGUMENT_VALUE, propertyName, message);
    }

    public InvalidValueFailInfo(String propertyName) 
    {
        this(FailType.INVALID_ARGUMENT_VALUE, propertyName);
    }

    public String getPropertyName()
    {
        return this.propertyName;
    }
    
}
