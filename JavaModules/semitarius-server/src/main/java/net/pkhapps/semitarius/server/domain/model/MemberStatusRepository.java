package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository of {@link MemberStatus}es.
 */
public interface MemberStatusRepository extends Repository<MemberStatus> {

    Optional<MemberStatus> findByMember(Member member);

    List<MemberStatus> findByMemberIn(Collection<Member> members);
}
