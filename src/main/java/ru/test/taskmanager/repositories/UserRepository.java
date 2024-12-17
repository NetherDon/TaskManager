package ru.test.taskmanager.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.test.taskmanager.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    public Optional<User> findByEmail(String email);

    public List<User> findByEmailIn(Collection<String> emails);
}
