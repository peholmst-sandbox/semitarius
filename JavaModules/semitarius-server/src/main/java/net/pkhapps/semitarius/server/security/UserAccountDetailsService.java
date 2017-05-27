package net.pkhapps.semitarius.server.security;

import net.pkhapps.semitarius.server.domain.model.UserAccount;
import net.pkhapps.semitarius.server.domain.model.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Implementation of {@link UserDetailsService} that delegates to a {@link UserAccountRepository}.
 */
class UserAccountDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    UserAccountDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final UserAccount userAccount = userAccountRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserAccountDetails(userAccount);
    }
}
