package ru.test.taskmanager.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.test.taskmanager.models.entities.User;
import ru.test.taskmanager.repositories.UserRepository;

@Service
public class UserService 
{
    @Autowired
    private UserRepository users;    

    public User getUser(String email)
    {
        return this.users.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("User not found")
        );
    }

    public List<User> getUsers(Collection<String> emails)
    {
        List<User> result = this.users.findByEmailIn(emails);
        if (result.size() != emails.size())
        {
            throw new UsernameNotFoundException("User not found");
        }
        return result;
    }
}
