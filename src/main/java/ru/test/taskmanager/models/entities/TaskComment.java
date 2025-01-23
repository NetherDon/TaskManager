package ru.test.taskmanager.models.entities;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = TaskComment.TABLE_NAME)
public class TaskComment 
{
    public static final String TABLE_NAME = "task_comments";
    
    public static final String ID_COLUMN = "id";
    public static final String TASK_COLUMN = "task";
    public static final String TEXT_COLUMN = "text";
    public static final String DATE_COLUMN = "date";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @NotNull
    private Task task;

    @ManyToOne
    @NotNull
    private User user;

    @NotBlank
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private ZonedDateTime date;

    protected TaskComment() {}

    public TaskComment(Task task, User user, String text, ZonedDateTime date)
    {
        this.task = task;
        this.user = user;
        this.text = text;
        this.date = date;
    }

    public long getId()
    {
        return this.id;
    }

    public Task getTask()
    {
        return this.task;
    }

    public void setTask(Task task)
    {
        this.task = task;
    }

    public User getUser()
    {
        return this.user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getText()
    {
        return this.text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public ZonedDateTime getDate()
    {
        return this.date;
    }

    public void setDate(ZonedDateTime date)
    {
        this.date = date;
    }
}
