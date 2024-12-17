package ru.test.taskmanager.models.responses;

import java.util.Collection;
import java.util.List;

public class FailResponse 
{
    private final Collection<? extends FailInfo> errors;

    private FailResponse(Collection<? extends FailInfo> errors) 
    {
        this.errors = errors;
    }

    public Collection<? extends FailInfo> getErrors()
    {
        return this.errors;
    }

    public static FailResponse of(Collection<? extends FailInfo> errors)
    {
        return new FailResponse(errors);
    }

    public static FailResponse of(FailInfo... errors)
    {
        return of(List.of(errors));
    }
}
