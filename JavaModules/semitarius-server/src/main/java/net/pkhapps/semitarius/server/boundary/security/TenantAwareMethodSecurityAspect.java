package net.pkhapps.semitarius.server.boundary.security;

import net.pkhapps.semitarius.server.domain.model.*;
import net.pkhapps.semitarius.server.security.UserAccountDetails;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Aspect that enforces security on {@link org.springframework.web.bind.annotation.RestController REST controllers}
 * as directed by the {@link RequireAnyRole} and {@link RequireAnyRoleOrCorrespondingMember} annotations. As an added
 * bonus, this aspect will also deny access to any REST controller methods that lack these annotations.
 */
@Aspect
@Component
class TenantAwareMethodSecurityAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantAwareMethodSecurityAspect.class);

    /**
     * Checks if the current user holds at least one of the specified {@link RequireAnyRole#value() roles}. The
     * current user is retrieved from the {@link SecurityContextHolder}. If any of the roles are tenant specific,
     * the current tenant is retrieved from the {@link JoinPoint#getArgs() method arguments}. This method also checks
     * that any {@link TenantOwnedAggregateRoot}s in the method arguments are owned by the current tenant.
     *
     * @throws AccessDeniedException if access is denied.
     */
    @Before(value = "@within(org.springframework.web.bind.annotation.RestController) && @annotation(annotation)",
            argNames = "jp, annotation")
    public void requireAnyRole(JoinPoint jp, RequireAnyRole annotation) throws Throwable {
        final Tenant tenant = getTenant(jp.getArgs());
        checkTenantSpecificArguments(tenant, jp.getArgs());
        checkCurrentUserRoles(tenant, annotation.value());
    }

    /**
     * The same as {@link #requireAnyRole(JoinPoint, RequireAnyRole)}, but also grants access if the current user
     * corresponds to the {@link Member} that the operation concerns (regardless of any roles). The member is
     * retrieved from the {@link JoinPoint#getArgs() method arguments}. The idea with this aspect is to e.g. allow an
     * admin to edit any member within the tenant, or an ordinary user to edit its own member data. For this to work,
     * the {@link Authentication#getPrincipal() principal} of the current
     * {@link SecurityContext#getAuthentication() authentication} must be an instance of {@link UserAccountDetails}.
     *
     * @throws AccessDeniedException if access is denied.
     */
    @Before(value = "@within(org.springframework.web.bind.annotation.RestController) && @annotation(annotation)",
            argNames = "jp, annotation")
    public void requireAnyRoleOrCorrespondingMember(JoinPoint jp, RequireAnyRoleOrCorrespondingMember annotation)
            throws Throwable {
        final Tenant tenant = getTenant(jp.getArgs());
        checkTenantSpecificArguments(tenant, jp.getArgs());
        final Member member = getMember(jp.getArgs());
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && member != null) {
            if (authentication.getPrincipal() instanceof UserAccountDetails) {
                UserAccount userAccount = ((UserAccountDetails) authentication.getPrincipal()).getUserAccount();
                Optional<Member> correspondingMember = userAccount.getMember();
                if (correspondingMember.isPresent() && correspondingMember.get().equals(member)) {
                    LOGGER.trace("Current user corresponds to member [{}], granting access", member);
                    return; // OK
                }
            } else {
                LOGGER.warn("Current authentication principal is NOT an instance of UserAccountDetails");
            }
        }
        checkCurrentUserRoles(tenant, annotation.value());
    }

    private static void checkTenantSpecificArguments(@Nullable Tenant tenant, @NotNull Object[] methodArguments) {
        if (tenant != null) {
            getArguments(methodArguments, TenantOwnedAggregateRoot.class).forEach(ar -> {
                if (!tenant.equals(ar.getTenant())) {
                    LOGGER.trace("Tenant was [{}], aggregate root [{}] is owned by [{}], denying access", tenant, ar,
                            ar.getTenant());
                    throw new AccessDeniedException("Tried to access data belonging to other tenant");
                } else {
                    LOGGER.trace("Aggregate root [{}] is owned by correct tenant [{}]", ar, tenant);
                }
            });
        }
    }

    private static void checkCurrentUserRoles(@Nullable Tenant tenant, @NotNull UserRole[] roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            for (UserRole role : roles) {
                GrantedAuthority authority = role.toGrantedAuthority(tenant);
                if (authentication.getAuthorities().contains(authority)) {
                    LOGGER.trace("Current user has required authority [{}], granting access", authority);
                    return;
                }
            }
        }
        LOGGER.trace("Denying access to user [{}]", authentication);
        throw new AccessDeniedException("The current user does not have permission to perform the operation");
    }

    /**
     * This aspect denies access to all methods that have not been annotated with either {@link RequireAnyRole} or
     * {@link RequireAnyRoleOrCorrespondingMember}. The idea is to prevent accidental security holes.
     */
    @Before(value = "@within(org.springframework.web.bind.annotation.RestController) && !@annotation(RequireAnyRole) " +
                    "&& !@annotation(RequireAnyRoleOrCorrespondingMember)")
    public void denyAccessToUnannotatedMethods(JoinPoint jp) throws Throwable {
        LOGGER.warn("Method [{}] is not protected, denying access by default", jp.getSignature());
        throw new AccessDeniedException(
                "No access rules have been specified for this method so access has been denied by default");
    }

    @Nullable
    private static Tenant getTenant(@NotNull Object[] methodArguments) {
        return getArgument(methodArguments, Tenant.class);
    }

    @Nullable
    private static Member getMember(@NotNull Object[] methodArguments) {
        return getArgument(methodArguments, Member.class);
    }

    @Nullable
    private static <T> T getArgument(@NotNull Object[] methodArguments, @NotNull Class<T> type) {
        return getArguments(methodArguments, type).findFirst().orElse(null);
    }

    @NotNull
    private static <T> Stream<T> getArguments(@NotNull Object[] methodArguments, @NotNull Class<T> type) {
        return Arrays.stream(methodArguments).filter(type::isInstance).map(type::cast);
    }
}
