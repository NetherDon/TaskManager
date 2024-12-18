package ru.test.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.test.taskmanager.models.entities.TaskComment;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long>
{
    
}
