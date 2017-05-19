package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository of {@link MemberLocation}s.
 */
public interface MemberLocationRepository extends Repository<MemberLocation> {

    Optional<MemberLocation> findByMember(Member member);

    List<MemberLocation> findByMemberIn(Collection<Member> members);
}
