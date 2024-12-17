package ru.test.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.test.taskmanager.models.entities.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> 
{
    
}
