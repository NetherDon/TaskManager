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
public final class UserRolePair implements GrantedAuthority
{
    public static final String TABLE_NAME = "roles";

    public static final String ID_COLUMN = "id";
    public static final String ROLE_COLUMN = "role";
    public static final String USER_COLUMN = "user";

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

    protected UserRolePair() {}

    public UserRolePair(User user, Role role)
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
        if (obj instanceof UserRolePair userRole)
        {
            return userRole.role.equals(this.role) 
                && userRole.user.equals(this.user);
        }

        return false;
    }
}
