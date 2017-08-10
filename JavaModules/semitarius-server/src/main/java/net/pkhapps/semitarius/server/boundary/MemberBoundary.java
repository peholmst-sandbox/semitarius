package net.pkhapps.semitarius.server.boundary;

import net.pkhapps.semitarius.server.boundary.dto.MemberDto;
import net.pkhapps.semitarius.server.boundary.security.RequireAnyRole;
import net.pkhapps.semitarius.server.boundary.security.RequireAnyRoleOrOwningUser;
import net.pkhapps.semitarius.server.domain.model.Member;
import net.pkhapps.semitarius.server.domain.model.MemberRepository;
import net.pkhapps.semitarius.server.domain.Tenant;
import net.pkhapps.semitarius.server.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST boundary for retrieving and updating members.
 *
 * @see Member
 */
@RestController
@RequestMapping(path = MemberBoundary.PATH)
class MemberBoundary {

    static final String PATH = "/api/1.0/{tenant}/members";

    // This boundary assumes that the number of members per tenant is so small that pagination is not needed.
    // If this turns out not to be the case, then this boundary will not perform well and pagination must be added.

    private final MemberRepository memberRepository;

    @Autowired
    MemberBoundary(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @GetMapping(path = "/{member}")
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @RequireAnyRole({UserRole.SYSADMIN, UserRole.TENANT_ADMIN, UserRole.TENANT_USER})
    public MemberDto getMember(@PathVariable("tenant") Tenant tenant,
                               @PathVariable("member") Member member) {
        return new MemberDto(member);
    }

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @RequireAnyRole({UserRole.SYSADMIN, UserRole.TENANT_ADMIN})
    public ResponseEntity<MemberDto> createMember(@PathVariable("tenant") Tenant tenant,
                                                  @RequestBody @Valid MemberDto body) {
        final Member member = new Member(tenant, body.firstName, body.lastName);
        member.setEmail(body.email);
        member.setPhoneNumber(body.phoneNumber);
        return new ResponseEntity<>(new MemberDto(memberRepository.save(member)), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{member}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @RequireAnyRoleOrOwningUser({UserRole.SYSADMIN, UserRole.TENANT_ADMIN})
    public ResponseEntity<MemberDto> updateMember(@PathVariable("tenant") Tenant tenant,
                                                  @PathVariable("member") Member member,
                                                  @RequestBody @Valid MemberDto body) {
        member.setFirstName(body.firstName);
        member.setLastName(body.lastName);
        member.setPhoneNumber(body.phoneNumber);
        member.setEmail(body.email);
        return new ResponseEntity<>(new MemberDto(memberRepository.save(member)), HttpStatus.OK);
    }

    // TODO Delete (hard or soft?)

    @GetMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @RequireAnyRole({UserRole.SYSADMIN, UserRole.TENANT_ADMIN, UserRole.TENANT_USER})
    public List<MemberDto> getMembers(@PathVariable("tenant") Tenant tenant) {
        return memberRepository.findByTenant(tenant).stream().map(MemberDto::new).collect(Collectors.toList());
    }
}
