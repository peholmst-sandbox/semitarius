package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.AggregateRoot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Optional;

/**
 * TODO Document me
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

    @NotNull
    public abstract String getUsername();

    @NotNull
    public abstract String getFullName();

    @NotNull
    public Optional<Tenant> getTenant() {
        return Optional.ofNullable(tenant);
    }

    @NotNull
    public abstract Collection<? extends GrantedAuthority> getAuthorities();
}
