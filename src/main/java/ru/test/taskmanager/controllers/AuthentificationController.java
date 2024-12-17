package ru.test.taskmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ru.test.taskmanager.models.entities.JwtDetails;
import ru.test.taskmanager.models.entities.User;
import ru.test.taskmanager.models.properties.FailType;
import ru.test.taskmanager.models.requests.UserCredentialsRequest;
import ru.test.taskmanager.models.responses.FailInfo;
import ru.test.taskmanager.models.responses.FailResponse;
import ru.test.taskmanager.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthentificationController 
{
    @Autowired
    private AuthenticationService authService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
        Authentication auth, 
        HttpServletRequest request
    )
    {
        this.authService.logout(auth.getUsername());
        request.getSession().invalidate();
        return ResponseEntity.ok("You have successfully logged out of your account");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid UserCredentialsRequest credentials)
    {   
        try
        {
            JwtDetails token = this.authService.login(credentials.getEmail(), credentials.getPassword());
            return ResponseEntity.ok(token.getToken());
        }
        catch (AuthenticationException exception)
        {
            return ResponseEntity.badRequest().body(FailResponse.of(
                new FailInfo(FailType.BAD_CREDENTIALS, "Bad Credentials")
            ));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@Valid UserCredentialsRequest credentials)
    {
        User user = new User(
            credentials.getEmail(),
            this.authService.encodePassword(credentials.getPassword())
        );
        this.authService.registerUser(user);
        return ResponseEntity.ok("New user successfully registered");
    }
}
