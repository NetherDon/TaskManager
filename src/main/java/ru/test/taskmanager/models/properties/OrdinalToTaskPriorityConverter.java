package ru.test.taskmanager.models.properties;

public class OrdinalToTaskPriorityConverter extends OrdinalToEnumConverter<TaskPriority>
{
    public OrdinalToTaskPriorityConverter() 
    {
        super(TaskPriority.class);
    }   
}
