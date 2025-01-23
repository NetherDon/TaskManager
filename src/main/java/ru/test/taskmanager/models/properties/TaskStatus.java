package ru.test.taskmanager.models.properties;

import java.util.Arrays;
import java.util.List;

public enum TaskStatus 
{
    PENDING(false, false),
    IN_PROGRESS(true, false),
    PAUSED(true, false),
    COMPLETE(true, true),
    CANCELED(false, true);

    private static List<TaskStatus> HIDDEN = null;

    private final boolean executorSettable;
    private final boolean hidden;

    private TaskStatus(boolean executorSettable, boolean hidden)
    {
        this.executorSettable = executorSettable;
        this.hidden = hidden;
    }

    public boolean canBeSetByExecutor()
    {
        return this.executorSettable;
    }

    public boolean isHiddenByDefault()
    {
        return this.hidden;
    }

    public static List<TaskStatus> getHiddenByDefaultValues()
    {
        if (HIDDEN == null)
        {
            HIDDEN = Arrays.stream(TaskStatus.values()).filter(TaskStatus::isHiddenByDefault).toList();
        }
        
        return HIDDEN;
    }
}
