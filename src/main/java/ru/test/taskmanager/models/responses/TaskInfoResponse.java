package ru.test.taskmanager.models.responses;

import java.util.List;

import org.springframework.lang.Nullable;

import ru.test.taskmanager.models.entities.Task;
import ru.test.taskmanager.models.properties.TaskPriority;

public class TaskInfoResponse
{
    private final Task task;
    
    public TaskInfoResponse(Task task)
    {
        this.task = task;
    }

    public long getId()
    {
        return this.task.getId();
    }

    public String getTitle()
    {
        return this.task.getTitle();
    }

    public String getDescription()
    {
        return this.task.getDescription();
    }

    public String getAuthor()
    {
        return this.task.getAuthor().getEmail();
    }

    @Nullable
    public TaskPriority getPriority()
    {
        return this.task.getPriority();
    }

    public List<String> getExecutors()
    {
        return this.task.getExecutors()
            .stream().map((taskIn) -> taskIn.getUser().getEmail())
            .toList();
    }
}
