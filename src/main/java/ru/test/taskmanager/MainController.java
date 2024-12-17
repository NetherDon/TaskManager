package ru.test.taskmanager;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController 
{
    @GetMapping("/test1")
    public String test() 
    {
        return "message";
    }

    @GetMapping("/test2")
    public String test2(Authentication authentication) 
    {
        return authentication.getName();
    }

    @GetMapping("/test3")
    public String test3(Authentication authentication) 
    {
        return authentication.getName() + " | dsadasd";
    }
}
