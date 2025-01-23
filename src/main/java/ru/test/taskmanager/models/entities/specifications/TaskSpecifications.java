package ru.test.taskmanager.models.entities.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import ru.test.taskmanager.models.entities.Task;
import ru.test.taskmanager.models.entities.TaskExecutor;
import ru.test.taskmanager.models.entities.User;
import ru.test.taskmanager.models.properties.TaskStatus;

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

    public static Specification<Task> hasStatus(@Nullable TaskStatus status)
    {
        return (root, query, builder) ->
        {
            var statusExp = root.get(Task.STATUS_COLUMN);
            if (status == null)
            {
                return builder.and(
                    TaskStatus.getHiddenByDefaultValues().stream()
                        .map((statusIn) -> builder.notEqual(statusExp, statusIn))
                        .toArray(Predicate[]::new)
                );
            }

            return builder.equal(statusExp, status);
        };
    }
}
