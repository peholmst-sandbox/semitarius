package net.pkhapps.semitarius.server.security;

import net.pkhapps.semitarius.server.domain.model.DeviceAccount;
import net.pkhapps.semitarius.server.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Objects;

/**
 * TODO document me
 */
public class LoginKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String loginKey;
    private final UserAccountDetails principal;

    public LoginKeyAuthenticationToken(@NotNull String loginKey) {
        super(null);
        this.loginKey = Strings.requireNonEmpty(loginKey, "loginKey must not be empty");
        this.principal = null;
        super.setAuthenticated(false);
    }

    public LoginKeyAuthenticationToken(@NotNull DeviceAccount deviceAccount) {
        super(Objects.requireNonNull(deviceAccount, "deviceAccount must not be null").getAuthorities());
        this.loginKey = deviceAccount.getLoginKey();
        this.principal = new UserAccountDetails(deviceAccount.getUserAccount());
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return getLoginKey();
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @NotNull
    public String getLoginKey() {
        return loginKey;
    }
}
