package net.pkhapps.semitarius.server.security;

import net.pkhapps.semitarius.server.domain.model.UserAccount;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 * Implementation of {@link UserDetails} that wraps a {@link UserAccount}.
 */
public class UserAccountDetails implements UserDetails {

    private final UserAccount userAccount;

    UserAccountDetails(@NotNull UserAccount userAccount) {
        this.userAccount = Objects.requireNonNull(userAccount, "userAccount must not be null");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAccount.getAuthorities();
    }

    @Override
    public String getPassword() {
        return userAccount.getPasswordHash().orElse(null);
    }

    @Override
    public String getUsername() {
        return userAccount.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @NotNull
    public UserAccount getUserAccount() {
        return userAccount;
    }
}
