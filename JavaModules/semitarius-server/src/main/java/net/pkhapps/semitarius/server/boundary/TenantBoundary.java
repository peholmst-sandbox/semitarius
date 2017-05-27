package net.pkhapps.semitarius.server.boundary;

import net.pkhapps.semitarius.server.boundary.dto.TenantDto;
import net.pkhapps.semitarius.server.boundary.security.RequireAnyRole;
import net.pkhapps.semitarius.server.domain.model.Tenant;
import net.pkhapps.semitarius.server.domain.model.TenantRepository;
import net.pkhapps.semitarius.server.domain.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST boundary for retrieving tenants.
 *
 * @see net.pkhapps.semitarius.server.domain.model.Tenant
 */
@RestController
@RequestMapping(path = TenantBoundary.PATH)
class TenantBoundary {

    // TODO Add methods for managing tenants as well

    static final String PATH = "/api/1.0/tenants";

    private final TenantRepository tenantRepository;

    @Autowired
    TenantBoundary(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @GetMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @RequireAnyRole({UserRole.SYSADMIN})
    public List<TenantDto> getTenants() {
        return tenantRepository.findAll().stream().map(TenantDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{tenant}")
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @RequireAnyRole({UserRole.SYSADMIN, UserRole.TENANT_ADMIN, UserRole.TENANT_USER})
    public TenantDto getTenant(@PathVariable("tenant") Tenant tenant) {
        return new TenantDto(tenant);
    }
}
