package ru.test.taskmanager.models.properties;

import org.springframework.stereotype.Component;

@Component
public class OrdinalToTaskStatusConverter extends OrdinalToEnumConverter<TaskStatus>
{
    
    public OrdinalToTaskStatusConverter() 
    {
        super(TaskStatus.class);
    }
    
}
