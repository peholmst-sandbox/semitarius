package net.pkhapps.semitarius.server.domain;

import net.pkhapps.semitarius.server.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * TODO Document me
 */
@Entity
@Table(name = UserAccount.TABLE_NAME)
@DiscriminatorValue("user")
public class UserAccount extends AbstractUser {

    static final String TABLE_NAME = "user_accounts";
    static final String COL_USERNAME = "username";
    static final String COL_PASSWORD = "password";
    static final String COL_FULL_NAME = "full_name";
    static final String COL_ROLE = "role";

    @Column(name = COL_USERNAME, unique = true, nullable = false)
    private String username;

    @Column(name = COL_PASSWORD)
    private String passwordHash;

    @Column(name = COL_FULL_NAME)
    private String fullName;

    @Column(name = COL_ROLE, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ConstructorUsedByJPAOnly
    @SuppressWarnings("unused")
    UserAccount() {
    }

    public UserAccount(@Nullable Tenant tenant, @NotNull String username, @NotNull String fullName,
                       @NotNull UserRole role) {
        super(tenant);
        this.username = Strings.requireNonEmpty(username, "username must not be empty");
        this.fullName = Strings.requireNonEmpty(fullName, "fullName must not be empty");
        if (tenant == null && role.isTenantSpecific()) {
            throw new IllegalArgumentException("Role must not be tenant specific");
        } else if (tenant != null && !role.isTenantSpecific()) {
            throw new IllegalArgumentException("Role must be tenant specific");
        }
        this.role = role;
    }

    @NotNull
    public Optional<String> getPasswordHash() {
        return Optional.ofNullable(passwordHash);
    }

    public void setPasswordHash(@Nullable String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    @NotNull
    public String getUsername() {
        return username;
    }

    @Override
    @NotNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@NotNull String fullName) {
        this.fullName = Strings.requireNonEmpty(fullName, "fullName must not be empty");
    }

    @NotNull
    public UserRole getRole() {
        return role;
    }

    @Override
    @NotNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole().toGrantedAuthority(getTenant().orElse(null)));
    }
}
