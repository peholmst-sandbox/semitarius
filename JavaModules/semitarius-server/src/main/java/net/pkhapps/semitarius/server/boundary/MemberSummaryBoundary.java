package net.pkhapps.semitarius.server.boundary;

import net.pkhapps.semitarius.server.boundary.dto.MemberDto;
import net.pkhapps.semitarius.server.boundary.dto.MemberLocationDto;
import net.pkhapps.semitarius.server.boundary.dto.MemberStatusDto;
import net.pkhapps.semitarius.server.boundary.dto.MemberSummaryDto;
import net.pkhapps.semitarius.server.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST boundary for retrieving summarized information about members, their status and location. This boundary is
 * read-only.
 *
 * @see Member
 * @see MemberLocation
 * @see MemberStatus
 */
@RestController
@RequestMapping(path = MemberBoundary.PATH)
class MemberSummaryBoundary {

    private final MemberRepository memberRepository;
    private final MemberLocationRepository memberLocationRepository;
    private final MemberStatusRepository memberStatusRepository;
    private final BoundaryUtils boundaryUtils;

    @Autowired
    MemberSummaryBoundary(MemberRepository memberRepository,
                          MemberLocationRepository memberLocationRepository,
                          MemberStatusRepository memberStatusRepository,
                          BoundaryUtils boundaryUtils) {
        this.memberRepository = memberRepository;
        this.memberLocationRepository = memberLocationRepository;
        this.memberStatusRepository = memberStatusRepository;
        this.boundaryUtils = boundaryUtils;
    }

    @GetMapping(path = "/{member}/summary")
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    // TODO Security
    public MemberSummaryDto getMemberSummary(@PathVariable("tenant") String tenantIdentifier,
                                             @PathVariable("member") Long memberId) {
        final Tenant tenant = boundaryUtils.findTenant(tenantIdentifier);
        final Member member = boundaryUtils.findMember(tenant, memberId);
        final MemberSummaryDto summaryDto = new MemberSummaryDto();
        summaryDto.member = new MemberDto(member);
        memberStatusRepository.findByMember(member).map(MemberStatusDto::new)
                .ifPresent(status -> summaryDto.status = status);
        memberLocationRepository.findByMember(member).map(MemberLocationDto::new)
                .ifPresent(location -> summaryDto.location = location);
        return summaryDto;
    }


    @GetMapping(path = "/summary")
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    // TODO Security
    public List<MemberSummaryDto> getMemberSummaries(@PathVariable("tenant") String tenantIdentifier) {
        final Tenant tenant = boundaryUtils.findTenant(tenantIdentifier);
        final List<Member> members = memberRepository.findByTenant(tenant);
        final Map<Member, MemberStatusDto> statusMap = memberStatusRepository.findByMemberIn(members).stream()
                .collect(Collectors.toMap(MemberStatus::getMember, MemberStatusDto::new));
        final Map<Member, MemberLocationDto> locationMap = memberLocationRepository.findByMemberIn(members).stream()
                .collect(Collectors.toMap(MemberLocation::getMember, MemberLocationDto::new));
        return members.stream()
                .map(member -> new MemberSummaryDto(new MemberDto(member), statusMap.get(member),
                        locationMap.get(member))).collect(Collectors.toList());
    }
}
