package ru.test.taskmanager.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_comments")
public class TaskComment 
{
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
