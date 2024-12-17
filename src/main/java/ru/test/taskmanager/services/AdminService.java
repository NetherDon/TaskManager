package ru.test.taskmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.test.taskmanager.models.entities.User;
import ru.test.taskmanager.models.entities.UserRole;
import ru.test.taskmanager.models.properties.Role;
import ru.test.taskmanager.repositories.UserRepository;
import ru.test.taskmanager.repositories.UserRoleRepository;

@Service
public class AdminService 
{
    @Autowired
    private UserRepository users;

    @Autowired
    private UserRoleRepository authorities;


    public boolean administer(String email)
    {
        User user = this.users.findByEmail(email).orElseThrow(
            () -> new IllegalArgumentException("The user \"" + email + "\" does not exist")
        );

        if (user.hasRole(Role.ADMIN))
        {
            return false;
        }

        UserRole authority = user.addRole(Role.ADMIN);
        try
        {
            this.authorities.save(authority);
            return true;
        }
        catch (Exception exception)
        {
            return false;
        }
    }
}
