package ru.test.taskmanager.models.properties;

import java.util.Optional;

public enum FailType 
{
    UNKNOWN(0),
    INVALID_ARGUMENT_VALUE(1, "invalid_value"),
    
    TASK_NOT_FOUND(100),
    UNABAILABLE_TASK_STATUS(101),
    USER_NOT_TASK_EXECUTOR(102),
    EXECUTOR_NOT_FOUND(103),
    EMPTY_EXECUTOR_LIST(104),
    
    BAD_CREDENTIALS(200),
    SAME_EMAIL(201);
    
    private final int code;
    private final Optional<String> name;

    private FailType(int code, String name)
    {
        this.code = code;
        this.name = Optional.ofNullable(name);
    }

    private FailType(int code)
    {
        this(code, null);
    }

    public int getCode()
    {
        return this.code;
    }

    public String getName()
    {
        return this.name.orElseGet(
            () -> this.name().toLowerCase()
        );
    }
}
