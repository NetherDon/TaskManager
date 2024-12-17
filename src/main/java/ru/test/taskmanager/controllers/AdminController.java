package ru.test.taskmanager.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.test.taskmanager.models.properties.TaskPriority;
import ru.test.taskmanager.services.AdminService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority(Role.ADMIN.name())")
public class AdminController 
{
    @Autowired
    private AdminService adminService;
    
    @PostMapping("/administer")
    public ResponseEntity<?> administer(@RequestParam(required = true) String email)
    {
        try
        {
            this.adminService.administer(email);
            return ResponseEntity.ok("The user with the email \"" + email + "\" is now an administrator");
        }
        catch (IllegalArgumentException exception)
        {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/tasks/create")
    public ResponseEntity<?> createTask(
        Authentication authentication,
        @RequestParam(required = true) String title,
        @RequestParam(required = true) String description,
        @RequestParam TaskPriority priority,
        @RequestParam(name = "executor", required = true) Set<String> executorEmails
    )
    {
        //Task task = this.adminService.createNewTask(authentication.getName(), title, description, priority, executorEmails);
        //return ResponseEntity.ok("Task \"" + task.getTitle() + "\" created");
        return ResponseEntity.ok("temp");
    }
}
