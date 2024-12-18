package ru.test.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.test.taskmanager.models.entities.UserRolePair;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRolePair, Long> 
{
    
}
