package net.pkhapps.semitarius.server.boundary.security;

import net.pkhapps.semitarius.server.domain.Tenant;
import net.pkhapps.semitarius.server.domain.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to secure methods of
 * {@link org.springframework.web.bind.annotation.RestController REST controllers}. The current user must hold any of
 * the specified roles to gain access. If the role is {@link UserRole#isTenantSpecific() tenant specific}, the
 * method must contain a parameter of type {@link Tenant} that contains the tenant in question. Even if the user does
 * not hold the necessary role, access can be granted if the user owns all of the
 * {@link net.pkhapps.semitarius.server.domain.UserOwnedAggregateRoot}s that are used as method parameters.
 *
 * @see RequireAnyRole
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAnyRoleOrOwningUser {
    UserRole[] value();
}
