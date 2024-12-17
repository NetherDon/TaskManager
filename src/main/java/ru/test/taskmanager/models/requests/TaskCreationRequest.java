package ru.test.taskmanager.models.requests;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.test.taskmanager.models.properties.TaskPriority;

public class TaskCreationRequest 
{
    @NotNull
    @Size(min = 3, message = "The length of the title cannot be less than 3")
    private String title;

    @NotNull
    private String description;
    
    private TaskPriority priority;

    @NotEmpty(message = "A task must have at least one executor")
    private Set<@NotBlank(message = "Executor email must not be blank") String> executor;

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public TaskPriority getPriority()
    {
        return this.priority;
    }

    public void setPriority(TaskPriority priority)
    {
        this.priority = priority;
    }

    public Set<String> getExecutorEmails()
    {
        return this.executor;
    }

    public void setExecutor(Set<String> emails)
    {
        this.executor = emails;
    }
}
