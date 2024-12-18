package ru.test.taskmanager.models.properties;

import org.springframework.lang.Nullable;

public interface ITaskFilter 
{
    @Nullable
    public TaskPriority getPriority();
    @Nullable
    public TaskStatus getStatus();
    public int getPage();
}
