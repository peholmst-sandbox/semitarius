package net.pkhapps.semitarius.server.boundary.security;

import net.pkhapps.semitarius.server.domain.model.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to secure methods of
 * {@link org.springframework.web.bind.annotation.RestController REST controllers}. The current user must hold any of
 * the specified roles to gain access. If the role is {@link UserRole#isTenantSpecific() tenant specific}, the
 * method must contain a parameter of type {@link net.pkhapps.semitarius.server.domain.model.Tenant} that contains
 * the tenant in question.
 *
 * @see RequireAnyRoleOrCorrespondingMember
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAnyRole {
    UserRole[] value();
}
