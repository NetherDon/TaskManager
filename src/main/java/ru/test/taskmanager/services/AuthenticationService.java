package ru.test.taskmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import ru.test.taskmanager.exceptions.auth.EmailDuplicateException;
import ru.test.taskmanager.exceptions.auth.RegistrationException;
import ru.test.taskmanager.models.entities.JwtDetails;
import ru.test.taskmanager.models.entities.User;
import ru.test.taskmanager.models.entities.UserRolePair;
import ru.test.taskmanager.repositories.JwtRepository;
import ru.test.taskmanager.repositories.UserRepository;
import ru.test.taskmanager.repositories.UserRoleRepository;
import ru.test.taskmanager.utils.JwtUtils;

@Service("userDetailsDervice")
public class AuthenticationService implements UserDetailsService
{
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UserRepository users;

    @Autowired
    private UserRoleRepository roles;

    @Autowired
    private JwtRepository authTokens;

    @Autowired
    private PasswordEncoder passEncoder;

    public void logout(User user)
    {
        this.authTokens.logoutUser(user);
    }

    public void logout(String email)
    {
        this.users.findByEmail(email).ifPresent(this::logout);
    }

    @Transactional
    public JwtDetails login(String email, String password)
    {
        User user = null;
        try
        {
            user = this.getUser(email);
        }
        catch(UsernameNotFoundException exception)
        {
            throw new BadCredentialsException(
                String.format("The user with e-mail \"%s\" does not exist", email),
                exception
            );
        }

        if (!user.getPassword().equals(password))
        {
            throw new BadCredentialsException("Incorrect password");
        }

        this.authTokens.logoutUser(user);
        
        JwtDetails token = new JwtDetails(
            user,
            this.generateJwtFor(email)
        );

        this.authTokens.save(token);

        return token;
    }

    public String generateJwtFor(String email)
    {
        return this.jwtUtils.generateTokenFor(email);
    }

    public boolean isEmailExists(String email)
    {
        return this.users.findByEmail(email).isPresent();
    }

    @Transactional
    public void registerUser(User user) throws RegistrationException
    {
        if (this.isEmailExists(user.getEmail()))
        {
            throw new EmailDuplicateException(
                user.getEmail(),
                String.format("A user with e-mail \"%s\" is already exists", user.getEmail())
            );
        }

        try
        {
            this.users.save(user);
            for (UserRolePair pair : user.getRoles())
            {
                this.roles.save(pair);
            }
        }
        catch (Exception exception)
        {
            throw new RegistrationException(
                "An unknown issues occurred during registration",
                exception
            );
        }
    }

    public String encodePassword(String rawPassword)
    {
        return this.passEncoder.encode(rawPassword);
    }

    public User getUser(String email) throws UsernameNotFoundException 
    {
        return this.users.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("User not found")
        );
    }

    @Transactional
    public UsernamePasswordAuthenticationToken createAuthenticationToken(String email, HttpServletRequest request)
    {
        UserDetails user = this.getUser(email);

        var token = new UsernamePasswordAuthenticationToken(
            user, user.getPassword(), user.getAuthorities()
        );
        token.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
    {
        return this.getUser(email);
    }
}
