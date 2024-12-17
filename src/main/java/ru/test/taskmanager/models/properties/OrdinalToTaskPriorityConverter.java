package ru.test.taskmanager.models.properties;

import org.springframework.stereotype.Component;

@Component
public class OrdinalToTaskPriorityConverter extends OrdinalToEnumConverter<TaskPriority>
{
    public OrdinalToTaskPriorityConverter() 
    {
        super(TaskPriority.class);
    }   
}
