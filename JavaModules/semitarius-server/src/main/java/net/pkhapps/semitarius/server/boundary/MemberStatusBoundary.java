package net.pkhapps.semitarius.server.boundary;

import net.pkhapps.semitarius.server.boundary.dto.MemberStatusDto;
import net.pkhapps.semitarius.server.boundary.exception.InvalidStatusDescriptorException;
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
 * REST boundary for retrieving and updating the status of a member.
 *
 * @see Member
 * @see MemberStatus
 */
@RestController
@RequestMapping(path = MemberBoundary.PATH)
class MemberStatusBoundary {

    private final MemberStatusRepository memberStatusRepository;
    private final StatusDescriptorRepository statusDescriptorRepository;
    private final BoundaryUtils boundaryUtils;
    private final Clock clock;

    @Autowired
    MemberStatusBoundary(MemberStatusRepository memberStatusRepository,
                         StatusDescriptorRepository statusDescriptorRepository,
                         BoundaryUtils boundaryUtils, Clock clock) {
        this.memberStatusRepository = memberStatusRepository;
        this.statusDescriptorRepository = statusDescriptorRepository;
        this.boundaryUtils = boundaryUtils;
        this.clock = clock;
    }

    @GetMapping(path = "/{member}/status")
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    // TODO Security
    public MemberStatusDto getMemberStatus(@PathVariable("tenant") String tenantIdentifier,
                                           @PathVariable("member") Long memberId) {
        final Tenant tenant = boundaryUtils.findTenant(tenantIdentifier);
        final Member member = boundaryUtils.findMember(tenant, memberId);
        return memberStatusRepository.findByMember(member).map(MemberStatusDto::new).orElseGet(MemberStatusDto::new);
    }

    @PutMapping(path = "/{member}/status")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    // TODO Security
    public ResponseEntity<Void> putMemberStatus(@PathVariable("tenant") String tenantIdentifier,
                                                @PathVariable("member") Long memberId,
                                                @RequestBody @Valid MemberStatusDto body) {
        final Tenant tenant = boundaryUtils.findTenant(tenantIdentifier);
        final Member member = boundaryUtils.findMember(tenant, memberId);
        MemberStatus memberStatus =
                memberStatusRepository.findByMember(member).orElseGet(() -> new MemberStatus(member, clock));
        StatusDescriptor status =
                statusDescriptorRepository.findByTenantAndId(tenant, body.statusDescriptorId).orElseThrow(
                        InvalidStatusDescriptorException::new);
        memberStatus.changeStatus(status, clock);
        memberStatusRepository.save(memberStatus);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
