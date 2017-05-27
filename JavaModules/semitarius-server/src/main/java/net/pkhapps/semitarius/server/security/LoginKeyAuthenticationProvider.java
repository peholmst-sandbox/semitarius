package net.pkhapps.semitarius.server.security;

import net.pkhapps.semitarius.server.domain.model.DeviceAccount;
import net.pkhapps.semitarius.server.domain.model.DeviceAccountRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Authentication provider that supports {@link LoginKeyAuthenticationToken}s. A token will be authenticated if its
 * {@link LoginKeyAuthenticationToken#getLoginKey() login key} is valid (i.e. exists in the database).
 */
class LoginKeyAuthenticationProvider implements AuthenticationProvider {

    private final DeviceAccountRepository deviceAccountRepository;

    LoginKeyAuthenticationProvider(DeviceAccountRepository deviceAccountRepository) {
        this.deviceAccountRepository = deviceAccountRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (supports(authentication.getClass())) {
            LoginKeyAuthenticationToken token = (LoginKeyAuthenticationToken) authentication;
            DeviceAccount deviceAccount = deviceAccountRepository.findByLoginKey(token.getLoginKey())
                    .orElseThrow(() -> new BadCredentialsException("Invalid login key"));
            return new LoginKeyAuthenticationToken(deviceAccount);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(LoginKeyAuthenticationToken.class);
    }
}
