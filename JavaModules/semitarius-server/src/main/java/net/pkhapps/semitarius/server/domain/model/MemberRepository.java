package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.Repository;
import net.pkhapps.semitarius.server.domain.Tenant;

import java.util.List;

/**
 * Repository of {@link Member}s.
 */
public interface MemberRepository extends Repository<Member> {

    List<Member> findByTenant(Tenant tenant);
}
