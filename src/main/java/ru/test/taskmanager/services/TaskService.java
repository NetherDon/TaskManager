package ru.test.taskmanager.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ru.test.taskmanager.exceptions.task.TaskNotFoundException;
import ru.test.taskmanager.exceptions.task.creation.EmptyExecutorListException;
import ru.test.taskmanager.exceptions.task.status.UnavailableTaskStatusException;
import ru.test.taskmanager.exceptions.task.status.UserNotTaskExecutorException;
import ru.test.taskmanager.models.entities.Task;
import ru.test.taskmanager.models.entities.TaskExecutor;
import ru.test.taskmanager.models.entities.User;
import ru.test.taskmanager.models.entities.specifications.CommonSpecifications;
import ru.test.taskmanager.models.entities.specifications.TaskSpecifications;
import ru.test.taskmanager.models.properties.ITaskFilter;
import ru.test.taskmanager.models.properties.Role;
import ru.test.taskmanager.models.properties.TaskPriority;
import ru.test.taskmanager.models.properties.TaskStatus;
import ru.test.taskmanager.repositories.TaskExecutorRepository;
import ru.test.taskmanager.repositories.TaskRepository;

@Service
public class TaskService 
{
    public static final int PAGE_SIZE = 5;

    @Autowired
    private TaskRepository tasks;

    @Autowired
    private TaskExecutorRepository taskExecutors;
    
    public Task getTask(long id)
    {
        return this.tasks.findById(id).orElseThrow(() ->
            new TaskNotFoundException("Task not found")
        );
    }

    public void setTaskStatus(long taskId, User user, TaskStatus status)
    {
        Task task = this.getTask(taskId);

        if (!user.hasRole(Role.ADMIN))
        {
            if (!task.isExecutor(user))
            {
                throw new UserNotTaskExecutorException();
            }

            if (!status.canBeSetByExecutor())
            {
                throw new UnavailableTaskStatusException(status);
            }
        }

        task.setStatus(status);
        this.tasks.save(task);
    }

    public List<Task> getTasksFor(User user, ITaskFilter filter)
    {
        System.out.println(filter.getPriority());
        Pageable pages = PageRequest.of(filter.getPage(), PAGE_SIZE, Sort.by(Order.desc(Task.PRIORITY_COLUMN)));
        Specification<Task> condition = Specification.where(
                user.hasRole(Role.ADMIN) ? null : TaskSpecifications.hasExecutor(user)
            )
            .and(CommonSpecifications.equalIfValueNotNull(Task.PRIORITY_COLUMN, filter.getPriority()))
            .and(CommonSpecifications.equalIfValueNotNull(Task.STATUS_COLUMN, filter.getStatus()));

        return this.tasks.findAll(condition, pages).toList();
    }

    public Task createNewTask(
        User author, 
        String title, 
        String description, 
        Optional<TaskPriority> priority, 
        Collection<User> executors
    )
    {
        Task task = new Task(author, title, description);
        priority.ifPresent(task::setPriority);

        if (executors.isEmpty())
        {
            throw new EmptyExecutorListException();
        }
        
        task.setExecutors(executors);

        this.saveTaskAndExcutors(task);
        return task;
    }

    private void saveTaskAndExcutors(Task task)
    {
        this.tasks.save(task);
        for (TaskExecutor executor : task.getExecutors())
        {
            this.taskExecutors.save(executor);
        }
    }
}
