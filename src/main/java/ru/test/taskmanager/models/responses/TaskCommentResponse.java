package ru.test.taskmanager.models.responses;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ru.test.taskmanager.models.entities.TaskComment;

public class TaskCommentResponse
{
    private final TaskComment comment;
    private boolean idVisible = true;

    public TaskCommentResponse(TaskComment comment)
    {
        this.comment = comment;
    }

    public String getText()
    {
        return this.comment.getText();
    }

    public ZonedDateTime getDate()
    {
        return this.comment.getDate();
    }

    public String getAuthor()
    {
        return this.comment.getUser().getEmail();
    }

    @JsonInclude(Include.NON_NULL)
    public long getId()
    {
        return this.idVisible ? this.comment.getId() : null;
    }

    @JsonIgnore
    public void setIdVisible(boolean flag)
    {
        this.idVisible = flag;
    }
}
