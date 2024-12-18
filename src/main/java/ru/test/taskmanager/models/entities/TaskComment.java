package ru.test.taskmanager.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = TaskComment.TABLE_NAME)
public class TaskComment 
{
    public static final String TABLE_NAME = "task_comments";
    
    public static final String ID_COLUMN = "id";
    public static final String TASK_COLUMN = "task";
    public static final String TEXT_COLUMN = "text";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    private Task task;
    private String text;

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

    public String getText()
    {
        return this.text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
