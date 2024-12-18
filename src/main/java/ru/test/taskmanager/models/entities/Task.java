package ru.test.taskmanager.models.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import ru.test.taskmanager.models.properties.TaskPriority;
import ru.test.taskmanager.models.properties.TaskStatus;

@Entity
@Table(name = Task.TABLE_NAME)
public class Task 
{
    public static final String TABLE_NAME = "tasks";

    public static final String ID_COLUMN = "id";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String STATUS_COLUMN = "status";
    public static final String PRIORITY_COLUMN = "priority";
    public static final String AUTHOR_COLUMN = "author";

    public static final String EXECUTORS_RELATION = "executors";
    public static final String COMMENTS_RELATION = "comments";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDING;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TaskPriority priority = TaskPriority.LOW;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User author;

    @OneToMany(mappedBy = "task")
    private Set<TaskExecutor> executors = new HashSet<>();

    @OneToMany(mappedBy = "task")
    @SuppressWarnings("FieldMayBeFinal")
    private Set<TaskComment> comments = new HashSet<>();

    protected Task() {}

    public Task(User author, String title, String description)
    {
        this.author = author;
        this.title = title;
        this.description = description;
    }

    public long getId()
    {
        return this.id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public TaskStatus getStatus()
    {
        return this.status;
    }

    public void setStatus(TaskStatus status)
    {
        this.status = status;
    }

    public TaskPriority getPriority()
    {
        return this.priority;
    }

    public void setPriority(TaskPriority priority)
    {
        this.priority = priority;
    }

    public User getAuthor()
    {
        return this.author;
    }

    public Set<TaskComment> getComments()
    {
        return this.comments;
    }

    public Set<TaskExecutor> getExecutors()
    {
        return this.executors;
    }

    public void setExecutors(Collection<User> executors)
    {
        this.executors = executors.stream()
            .map((user) -> new TaskExecutor(this, user))
            .collect(Collectors.toSet());
    }

    public boolean isExecutor(User user)
    {
        return this.executors.stream().anyMatch(
            (executor) -> executor.getUser().getEmail().equals(user.getEmail())
        );
    }
}
