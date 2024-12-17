package ru.test.taskmanager.models.properties;

public enum TaskStatus 
{
    PENDING(false),
    IN_PROGRESS(true),
    PAUSED(true),
    COMPLETE(true),
    CANCELED(false);

    private final boolean executorSettable;

    private TaskStatus(boolean executorSettable)
    {
        this.executorSettable = executorSettable;
    }

    public boolean canBeSetByExecutor()
    {
        return this.executorSettable;
    }
}
