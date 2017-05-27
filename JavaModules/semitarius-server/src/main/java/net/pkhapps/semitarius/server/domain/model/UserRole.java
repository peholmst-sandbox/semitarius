package net.pkhapps.semitarius.server.domain.model;

import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Objects;

/**
 * TODO Document me
 */
public enum UserRole {
    SYSADMIN(false), TENANT_ADMIN(true), TENANT_USER(true);

    final boolean tenantSpecific;

    UserRole(boolean tenantSpecific) {
        this.tenantSpecific = tenantSpecific;
    }

    public boolean isTenantSpecific() {
        return tenantSpecific;
    }

    public GrantedAuthority toGrantedAuthority(@Nullable Tenant tenant) {
        if (tenantSpecific) {
            Objects.requireNonNull(tenant, "tenant must not be null");
            return new SimpleGrantedAuthority("ROLE_" + tenant.getIdentifier() + "_" + name());
        } else {
            return new SimpleGrantedAuthority("ROLE_" + name());
        }
    }
}
