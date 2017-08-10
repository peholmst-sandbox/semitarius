package net.pkhapps.semitarius.server.security;

import net.pkhapps.semitarius.server.domain.DeviceAccount;
import net.pkhapps.semitarius.server.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Objects;

/**
 * Authentication token that can be used both as a login key authentication request and as an authenticated token
 * (after successful authentication).
 *
 * @see LoginKeyAuthenticationFilter
 * @see LoginKeyAuthenticationProvider
 * @see DeviceAccount
 */
public class LoginKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String loginId;
    private final String loginKey;
    private final UserAccountDetails principal;

    /**
     * Creates a new authentication request.
     *
     * @param loginId  the login ID taken from the HTTP request header.
     * @param loginKey the login key taken from the HTTP request header.
     * @see LoginKeyAuthenticationFilter
     */
    LoginKeyAuthenticationToken(@NotNull String loginId, @NotNull String loginKey) {
        super(null);
        this.loginId = Strings.requireNonEmpty(loginId, "loginId must not be null");
        this.loginKey = Strings.requireNonEmpty(loginKey, "loginKey must not be empty");
        this.principal = null;
        super.setAuthenticated(false);
    }

    /**
     * Creates a new authenticated authentication token. The {@link #getPrincipal() principal} will be a
     * {@link UserAccountDetails} instance.
     *
     * @param deviceAccount the device account that corresponds to the login key.
     * @see LoginKeyAuthenticationProvider
     */
    LoginKeyAuthenticationToken(@NotNull DeviceAccount deviceAccount) {
        super(Objects.requireNonNull(deviceAccount, "deviceAccount must not be null").getAuthorities());
        this.loginKey = deviceAccount.getLoginKey();
        this.loginId = deviceAccount.getLoginId();
        this.principal = new UserAccountDetails(deviceAccount.getUserAccount());
        super.setAuthenticated(true);
    }

    /**
     * The credentials are always the {@link #getLoginKey() login key}.
     * <p>
     * Inherited JavaDocs: {@inheritDoc}
     */
    @Override
    public Object getCredentials() {
        return getLoginKey();
    }

    /**
     * If the token is an authentication request, the principal is the login ID. If the token is a
     * validated authentication token, the principal is an instance of {@link UserAccountDetails}.
     * <p>
     * Inherited JavaDocs: {@inheritDoc}
     */
    @Override
    public Object getPrincipal() {
        return principal != null ? principal : loginId;
    }

    /**
     * Returns the login key.
     *
     * @see DeviceAccount#getLoginKey()
     */
    @NotNull
    String getLoginKey() {
        return loginKey;
    }

    /**
     * Returns the login ID.
     *
     * @see DeviceAccount#getLoginId()
     */
    @NotNull
    String getLoginId() {
        return loginId;
    }
}
