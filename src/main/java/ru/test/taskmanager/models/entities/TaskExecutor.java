package ru.test.taskmanager.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_executors")
public class TaskExecutor 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public TaskExecutor() {}

    public TaskExecutor(Task task, User user)
    {
        this.task = task;
        this.user = user;
    }

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
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
}
