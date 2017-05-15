package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository of {@link Member}s.
 */
public interface MemberRepository extends Repository<Member> {

    Optional<Member> findByTenantAndId(Tenant tenant, Long id);

    List<Member> findByTenant(Tenant tenant);
}
