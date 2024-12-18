package ru.test.taskmanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.test.taskmanager.exceptions.task.creation.TaskExecutorNotFoundException;
import ru.test.taskmanager.models.entities.Task;
import ru.test.taskmanager.models.entities.User;
import ru.test.taskmanager.models.properties.TaskStatus;
import ru.test.taskmanager.models.requests.TaskCreationRequest;
import ru.test.taskmanager.models.requests.TaskListFilterRequest;
import ru.test.taskmanager.models.responses.TaskCreatedResponse;
import ru.test.taskmanager.models.responses.TaskInfoResponse;
import ru.test.taskmanager.services.TaskService;
import ru.test.taskmanager.services.UserService;

@RestController
@RequestMapping("/tasks")
public class TaskController 
{
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(
        @AuthenticationPrincipal User author,
        @Valid TaskCreationRequest info
    )
    {
        List<User> executors;
        try
        {
            executors = this.userService.getUsers(info.getExecutorEmails());
        }
        catch(UsernameNotFoundException exception)
        {
            throw new TaskExecutorNotFoundException();
        }

        Task task = this.taskService.createNewTask(
            author, 
            info.getTitle(), 
            info.getDescription(), 
            Optional.ofNullable(info.getPriority()), 
            executors
        );
        
        return ResponseEntity.ok(
            new TaskCreatedResponse(task.getId(), "Task succesfully created")
        );
    }

    @GetMapping
    public ResponseEntity<?> getList(
        @AuthenticationPrincipal User user,
        @Valid TaskListFilterRequest filter
    )
    {
        return ResponseEntity.ok(
            this.taskService.getTasksFor(user, filter)
                .stream().map(TaskInfoResponse::new)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(
        @AuthenticationPrincipal User user,
        @PathVariable(required = true) long id
    ) 
    {
        Task task = this.taskService.getTask(id, user);
        return ResponseEntity.ok(new TaskInfoResponse(task));
    }

    @PostMapping("/{id}/set-status")
    public ResponseEntity<?> changeTaskStatus(
        @AuthenticationPrincipal User user,
        @PathVariable(name = "id", required = true) long taskId,
        @RequestParam(required = true) TaskStatus status
    )
    {
        Task task = this.taskService.getTask(taskId, user);
        this.taskService.setTaskStatus(task, status, user);
        return ResponseEntity.ok("Task status changed to '" + status + "'");
    }
}
