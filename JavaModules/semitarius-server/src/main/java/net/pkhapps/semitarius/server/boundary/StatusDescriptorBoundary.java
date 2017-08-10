package net.pkhapps.semitarius.server.boundary;

import net.pkhapps.semitarius.server.boundary.dto.StatusDescriptorDto;
import net.pkhapps.semitarius.server.boundary.security.RequireAnyRole;
import net.pkhapps.semitarius.server.domain.model.StatusDescriptorRepository;
import net.pkhapps.semitarius.server.domain.Tenant;
import net.pkhapps.semitarius.server.domain.UserRole;
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
 * REST boundary for retrieving status descriptors.
 *
 * @see net.pkhapps.semitarius.server.domain.model.StatusDescriptor
 */
@RestController
@RequestMapping(path = StatusDescriptorBoundary.PATH)
class StatusDescriptorBoundary {

    // TODO Add methods for managing status descriptors as well

    static final String PATH = "/api/1.0/{tenant}/statusDescriptors";

    private final StatusDescriptorRepository statusDescriptorRepository;

    @Autowired
    StatusDescriptorBoundary(
            StatusDescriptorRepository statusDescriptorRepository) {
        this.statusDescriptorRepository = statusDescriptorRepository;
    }

    @GetMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @RequireAnyRole({UserRole.SYSADMIN, UserRole.TENANT_ADMIN, UserRole.TENANT_USER})
    public List<StatusDescriptorDto> getStatusDescriptors(@PathVariable("tenant") Tenant tenant) {
        return statusDescriptorRepository.findByTenant(tenant).stream().map(StatusDescriptorDto::new)
                .collect(Collectors.toList());
    }
}
