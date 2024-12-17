package ru.test.taskmanager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import ru.test.taskmanager.models.entities.JwtDetails;
import ru.test.taskmanager.models.entities.User;

@Repository
public interface JwtRepository extends JpaRepository<JwtDetails, Long>
{
    public Optional<JwtDetails> findByToken(String token);

    @Query("select t from JwtDetails t where t.user = :user and t.active")
    public List<JwtDetails> findActiveTokens(@Param("user") User user);

    @Transactional
    @Modifying
    @Query("update JwtDetails t set t.active = false where t.user = :user")
    public void logoutUser(@Param("user") User user);
}
