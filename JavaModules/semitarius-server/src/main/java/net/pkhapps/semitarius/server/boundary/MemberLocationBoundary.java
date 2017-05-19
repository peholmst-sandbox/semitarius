package net.pkhapps.semitarius.server.boundary;

import net.pkhapps.semitarius.server.boundary.dto.MemberLocationDto;
import net.pkhapps.semitarius.server.domain.model.Member;
import net.pkhapps.semitarius.server.domain.model.MemberLocation;
import net.pkhapps.semitarius.server.domain.model.MemberLocationRepository;
import net.pkhapps.semitarius.server.domain.model.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Clock;

/**
 * REST boundary for retrieving and updating the location of a member.
 *
 * @see Member
 * @see MemberLocation
 */
@RestController
@RequestMapping(path = MemberBoundary.PATH)
class MemberLocationBoundary {

    private final MemberLocationRepository memberLocationRepository;
    private final BoundaryUtils boundaryUtils;
    private final Clock clock;

    @Autowired
    MemberLocationBoundary(MemberLocationRepository memberLocationRepository,
                           BoundaryUtils boundaryUtils, Clock clock) {
        this.memberLocationRepository = memberLocationRepository;
        this.boundaryUtils = boundaryUtils;
        this.clock = clock;
    }

    @GetMapping(path = "/{member}/location")
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    // TODO Security
    public MemberLocationDto getMemberLocation(@PathVariable("tenant") String tenantIdentifier,
                                               @PathVariable("member") Long memberId) {
        final Tenant tenant = boundaryUtils.findTenant(tenantIdentifier);
        final Member member = boundaryUtils.findMember(tenant, memberId);
        return memberLocationRepository.findByMember(member).map(MemberLocationDto::new)
                .orElseGet(MemberLocationDto::new);
    }

    @PutMapping(path = "/{member}/location")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    // TODO Security
    public ResponseEntity<Void> putMemberLocation(@PathVariable("tenant") String tenantIdentifier,
                                                  @PathVariable("member") Long memberId,
                                                  @RequestBody @Valid MemberLocationDto body) {
        final Tenant tenant = boundaryUtils.findTenant(tenantIdentifier);
        final Member member = boundaryUtils.findMember(tenant, memberId);
        MemberLocation memberLocation =
                memberLocationRepository.findByMember(member).orElseGet(() -> new MemberLocation(member, clock));
        memberLocation.changeDistance(body.distanceToStation, clock);
        memberLocationRepository.save(memberLocation);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
