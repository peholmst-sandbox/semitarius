package net.pkhapps.semitarius.server.boundary;

import net.pkhapps.semitarius.server.boundary.dto.MemberLocationDto;
import net.pkhapps.semitarius.server.boundary.security.RequireAnyRole;
import net.pkhapps.semitarius.server.boundary.security.RequireAnyRoleOrOwningUser;
import net.pkhapps.semitarius.server.domain.Tenant;
import net.pkhapps.semitarius.server.domain.UserRole;
import net.pkhapps.semitarius.server.domain.model.*;
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
    private final Clock clock;

    @Autowired
    MemberLocationBoundary(MemberLocationRepository memberLocationRepository, Clock clock) {
        this.memberLocationRepository = memberLocationRepository;
        this.clock = clock;
    }

    @GetMapping(path = "/{member}/location")
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @RequireAnyRole({UserRole.SYSADMIN, UserRole.TENANT_ADMIN, UserRole.TENANT_USER})
    public MemberLocationDto getMemberLocation(@PathVariable("tenant") Tenant tenant,
                                               @PathVariable("member") Member member) {
        return memberLocationRepository.findByMember(member).map(MemberLocationDto::new)
                .orElseGet(MemberLocationDto::new);
    }

    @PutMapping(path = "/{member}/location")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @RequireAnyRoleOrOwningUser({UserRole.SYSADMIN, UserRole.TENANT_ADMIN})
    public ResponseEntity<Void> putMemberLocation(@PathVariable("tenant") Tenant tenant,
                                                  @PathVariable("member") Member member,
                                                  @RequestBody @Valid MemberLocationDto body) {
        MemberLocation memberLocation =
                memberLocationRepository.findByMember(member).orElseGet(() -> new MemberLocation(member, clock));
        memberLocation.changeDistance(body.distanceToStation, clock);
        memberLocationRepository.save(memberLocation);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
