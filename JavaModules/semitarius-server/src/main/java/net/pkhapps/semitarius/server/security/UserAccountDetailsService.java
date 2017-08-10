package net.pkhapps.semitarius.server.security;

import net.pkhapps.semitarius.server.domain.UserAccount;
import net.pkhapps.semitarius.server.domain.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link UserDetailsService} that delegates to a {@link UserAccountRepository}.
 */
class UserAccountDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    UserAccountDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final UserAccount userAccount = userAccountRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserAccountDetails(userAccount);
    }
}
