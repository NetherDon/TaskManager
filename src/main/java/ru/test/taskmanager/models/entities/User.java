package ru.test.taskmanager.models.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import ru.test.taskmanager.models.properties.Role;

@Entity
@Table(name = "users")
public final class User implements UserDetails
{
    public static final int MIN_PASSWORD_SIZE = 5;

    public static final String PASSWORD_REGEX = "^[a-zA-Z0-9#?!@$%^&*-]{" + MIN_PASSWORD_SIZE + ",}$";
    public static final java.util.regex.Pattern PASSWORD_PATTERN = java.util.regex.Pattern.compile(PASSWORD_REGEX);

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+$";
    public static final java.util.regex.Pattern EMAIL_PATTERN = java.util.regex.Pattern.compile(EMAIL_REGEX);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = EMAIL_REGEX)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean enabled = true;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> roles;

    protected User()
    {
        this.roles = new HashSet<>(List.of(
            new UserRole(this, Role.USER)
        ));
    }

    public User(String email, String password)
    {
        this();
        this.email = email;
        this.password = password;
    }

    public long getId()
    {
        return this.id;
    }

    public String getEmail()
    {
        return this.email;
    }

    @Override
    public String getUsername() 
    {
        return this.getEmail();
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    @Override
    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setRoles(Collection<Role> roles)
    {
        this.roles = roles.stream()
            .map((role) -> new UserRole(this, role))
            .collect(Collectors.toSet());
    }

    public void addRoles(Role... roles)
    {
        this.addRoles(Arrays.asList(roles));
    }

    public void addRoles(Collection<Role> roles)
    {
        for (Role role : roles)
        {
            this.addRole(role);
        }
    }

    public UserRole addRole(Role role)
    {
        UserRole authority = new UserRole(this, role);
        this.roles.add(authority);
        return authority;
    }

    public void removeRoles(Collection<Role> roles)
    {
        this.roles = this.roles.stream()
            .filter((authority) -> roles.contains(authority.getRole()))
            .collect(Collectors.toSet());
    }

    public Collection<UserRole> getRoles()
    {
        return this.roles;
    }

    @Override
    public boolean isEnabled()
    {
        return this.enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    {
        return this.roles;
    }

    public boolean hasRole(Role role)
    {
        return this.roles.stream().anyMatch(
            (authority) -> authority.getRole() == role
        );
    }
}
