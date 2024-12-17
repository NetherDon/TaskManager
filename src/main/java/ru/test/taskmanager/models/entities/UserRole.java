package ru.test.taskmanager.models.entities;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ru.test.taskmanager.models.properties.Role;

@Entity
@Table(name = "roles")
public final class UserRole implements GrantedAuthority
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SuppressWarnings("unused")
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Role role;

    protected UserRole() {}

    public UserRole(User user, Role role)
    {
        this.user = user;
        this.role = role;
    }

    public User getUser()
    {
        return this.user;
    }

    public Role getRole()
    {
        return this.role;
    }

    @Override
    public final String getAuthority()
    {
        return this.getRole().name();
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(this.user, this.role);
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (obj == this) return true;
        if (obj instanceof UserRole userRole)
        {
            return userRole.role.equals(this.role) 
                && userRole.user.equals(this.user);
        }

        return false;
    }
}
