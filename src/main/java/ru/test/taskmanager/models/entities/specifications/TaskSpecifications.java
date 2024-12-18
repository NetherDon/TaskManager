package ru.test.taskmanager.models.entities.specifications;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import ru.test.taskmanager.models.entities.Task;
import ru.test.taskmanager.models.entities.TaskExecutor;
import ru.test.taskmanager.models.entities.User;

public final class TaskSpecifications 
{
    public static Specification<Task> hasExecutor(User user)
    {
        return (root, query, builder) ->
        {
            Join<TaskExecutor, Task> executorsJoin = root.join(Task.EXECUTORS_RELATION);
            return builder.equal(executorsJoin.get(TaskExecutor.USER_COLUMN), user);
        };
    };
}
