package net.pkhapps.semitarius.server.domain.model;

import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Objects;

/**
 * Enumeration of user roles. The roles can be converted to {@link GrantedAuthority} so that they can be plugged into
 * Spring Security.
 */
public enum UserRole {
    /**
     * The user is a global system administrator and does not belong to any tenant.
     */
    SYSADMIN(false),

    /**
     * The user is an administrator for a particular tenant.
     */
    TENANT_ADMIN(true),

    /**
     * The user is an ordinary user belonging to a particular tenant.
     */
    TENANT_USER(true);

    final boolean tenantSpecific;

    UserRole(boolean tenantSpecific) {
        this.tenantSpecific = tenantSpecific;
    }

    /**
     * Returns whether this role is tenant specific or not. A tenant specific role requires a {@link Tenant} to
     * be able to create a {@link GrantedAuthority}.
     *
     * @see #toGrantedAuthority(Tenant)
     */
    public boolean isTenantSpecific() {
        return tenantSpecific;
    }

    /**
     * Converts the role to a {@link GrantedAuthority} that can be used for access controls.
     *
     * @param tenant the current tenant if the role is tenant specific, otherwise {@code null}.
     * @return the granted authority.
     * @see #isTenantSpecific()
     */
    public GrantedAuthority toGrantedAuthority(@Nullable Tenant tenant) {
        if (tenantSpecific) {
            Objects.requireNonNull(tenant, "tenant must not be null");
            return new SimpleGrantedAuthority("ROLE_" + tenant.getIdentifier() + "_" + name());
        } else {
            return new SimpleGrantedAuthority("ROLE_" + name());
        }
    }
}
