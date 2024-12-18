package ru.test.taskmanager.models.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import ru.test.taskmanager.models.properties.ITaskFilter;
import ru.test.taskmanager.models.properties.TaskPriority;
import ru.test.taskmanager.models.properties.TaskStatus;

public class TaskListFilterRequest implements ITaskFilter
{
    @Nullable
    private TaskPriority priority;

    @Nullable
    private TaskStatus status;

    @Min(value = 0, message = "Page can't be negative")
    private int page = 0;

    @Override
    public TaskPriority getPriority() 
    {
        return this.priority;
    }

    public void setPriority(TaskPriority priority)
    {
        this.priority = priority;
    }

    @Override
    public TaskStatus getStatus() 
    {
        return this.status;
    }

    public void setStatus(TaskStatus status)
    {
        this.status = status;
    }

    @Override
    public int getPage() 
    {
        return this.page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

}
