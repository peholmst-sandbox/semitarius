package net.pkhapps.semitarius.server.domain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Optional;

/**
 * Base class for user accounts.
 */
@Entity
@Table(name = AbstractUser.TABLE_NAME)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class AbstractUser extends AggregateRoot {

    static final String TABLE_NAME = "users";
    static final String COL_TENANT = "tenant_id";

    @ManyToOne
    @JoinColumn(name = COL_TENANT)
    private Tenant tenant;

    public AbstractUser() {
    }

    public AbstractUser(@Nullable Tenant tenant) {
        this.tenant = tenant;
    }

    /**
     * Returns the username of the user account. This name is unique and is used to identify the user.
     */
    @NotNull
    public abstract String getUsername();

    /**
     * Returns the full name of the user account. Since users need not be humans, there is no separation into first
     * and last name.
     */
    @NotNull
    public abstract String getFullName();

    /**
     * Returns the tenant that this user belongs to, if applicable. Normally, all users but system administrators
     * belong to a tenant. In the future, we may support multiple tenants per user but for now, this will do.
     */
    @NotNull
    public Optional<Tenant> getTenant() {
        return Optional.ofNullable(tenant);
    }

    /**
     * Returns the granted authorities of the user.
     */
    @NotNull
    public abstract Collection<? extends GrantedAuthority> getAuthorities();
}
