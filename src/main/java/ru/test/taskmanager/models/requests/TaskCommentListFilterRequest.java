package ru.test.taskmanager.models.requests;

import jakarta.validation.constraints.Min;
import ru.test.taskmanager.models.properties.ITaskCommentFilter;

public class TaskCommentListFilterRequest implements ITaskCommentFilter
{
    private boolean newest = false;

    @Min(value = 0, message = "Page can't be negative")
    private int page = 0;

    @Override
    public boolean isSortingByNewestEnabled() 
    {
        return this.newest;
    }

    public void setNewest(boolean flag)
    {
        this.newest = flag;
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
