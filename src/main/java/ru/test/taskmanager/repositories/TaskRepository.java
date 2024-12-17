package ru.test.taskmanager.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.test.taskmanager.models.entities.Task;
import ru.test.taskmanager.models.entities.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> 
{
    public List<Task> findByExecutorsUser(@Param("user") User user, Pageable page);
}
