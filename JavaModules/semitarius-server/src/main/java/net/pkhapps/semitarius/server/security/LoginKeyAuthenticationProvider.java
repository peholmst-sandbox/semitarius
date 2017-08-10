package net.pkhapps.semitarius.server.security;

import net.pkhapps.semitarius.server.domain.DeviceAccount;
import net.pkhapps.semitarius.server.domain.DeviceAccountRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authentication provider that supports {@link LoginKeyAuthenticationToken}s. A token will be authenticated if its
 * {@link LoginKeyAuthenticationToken#getLoginId() login ID} and
 * {@link LoginKeyAuthenticationToken#getLoginKey() login key} are valid (i.e. exists in the database). If the ID is
 * valid but the key is not, the corresponding {@link DeviceAccount} will be immediately
 * {@link DeviceAccount#lock() locked} since the key should always be known when attempting authentication (it is
 * used for devices and is not memorized by a human).
 */
class LoginKeyAuthenticationProvider implements AuthenticationProvider {

    private final DeviceAccountRepository deviceAccountRepository;

    LoginKeyAuthenticationProvider(DeviceAccountRepository deviceAccountRepository) {
        this.deviceAccountRepository = deviceAccountRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (supports(authentication.getClass())) {
            final LoginKeyAuthenticationToken token = (LoginKeyAuthenticationToken) authentication;
            final DeviceAccount deviceAccount = deviceAccountRepository.findByLoginId(token.getLoginId())
                    .orElseThrow(() -> new BadCredentialsException("Invalid login ID"));
            if (deviceAccount.isLocked()) {
                throw new LockedException("The account is locked");
            }
            if (deviceAccount.getLoginKey().equals(token.getLoginKey())) {
                return new LoginKeyAuthenticationToken(deviceAccount);
            } else {
                deviceAccount.lock();
                deviceAccountRepository.save(deviceAccount);
                throw new BadCredentialsException("Invalid login key, account has been locked");
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(LoginKeyAuthenticationToken.class);
    }
}
