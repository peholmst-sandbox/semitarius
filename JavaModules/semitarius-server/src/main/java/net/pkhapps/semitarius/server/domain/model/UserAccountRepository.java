package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.Repository;

import java.util.Optional;

/**
 * Repository of {@link UserAccount}s.
 */
public interface UserAccountRepository extends Repository<UserAccount> {

    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByMember(Member member);
}
