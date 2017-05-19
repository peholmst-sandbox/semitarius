package net.pkhapps.semitarius.server.boundary;

import net.pkhapps.semitarius.server.boundary.exception.MemberNotFoundException;
import net.pkhapps.semitarius.server.boundary.exception.TenantNotFoundException;
import net.pkhapps.semitarius.server.domain.model.Member;
import net.pkhapps.semitarius.server.domain.model.MemberRepository;
import net.pkhapps.semitarius.server.domain.model.Tenant;
import net.pkhapps.semitarius.server.domain.model.TenantRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Utility methods commonly used by REST boundaries.
 */
@Service
class BoundaryUtils {

    private final TenantRepository tenantRepository;
    private final MemberRepository memberRepository;

    @Autowired
    BoundaryUtils(TenantRepository tenantRepository,
                  MemberRepository memberRepository) {
        this.tenantRepository = tenantRepository;
        this.memberRepository = memberRepository;
    }

    @NotNull
    Tenant findTenant(@NotNull String identifier) {
        return tenantRepository.findByIdentifier(identifier).orElseThrow(TenantNotFoundException::new);
    }

    @NotNull
    Member findMember(@NotNull Tenant tenant, @NotNull Long memberId) {
        return memberRepository.findByTenantAndId(tenant, memberId).orElseThrow(MemberNotFoundException::new);
    }
}
