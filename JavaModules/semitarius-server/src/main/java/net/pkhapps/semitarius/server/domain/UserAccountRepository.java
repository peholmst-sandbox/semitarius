package net.pkhapps.semitarius.server.domain;

import java.util.Optional;

/**
 * Repository of {@link UserAccount}s.
 */
public interface UserAccountRepository extends Repository<UserAccount> {

    Optional<UserAccount> findByUsername(String username);
}
