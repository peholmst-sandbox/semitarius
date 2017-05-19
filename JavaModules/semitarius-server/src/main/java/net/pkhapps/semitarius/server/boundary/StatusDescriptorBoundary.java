package net.pkhapps.semitarius.server.boundary;

import net.pkhapps.semitarius.server.boundary.dto.StatusDescriptorDto;
import net.pkhapps.semitarius.server.domain.model.StatusDescriptorRepository;
import net.pkhapps.semitarius.server.domain.model.Tenant;
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
    private final BoundaryUtils boundaryUtils;

    @Autowired
    StatusDescriptorBoundary(
            StatusDescriptorRepository statusDescriptorRepository,
            BoundaryUtils boundaryUtils) {
        this.statusDescriptorRepository = statusDescriptorRepository;
        this.boundaryUtils = boundaryUtils;
    }

    @GetMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    // TODO Security
    public List<StatusDescriptorDto> getStatusDescriptors(@PathVariable("tenant") String tenantIdentifier) {
        final Tenant tenant = boundaryUtils.findTenant(tenantIdentifier);
        return statusDescriptorRepository.findByTenant(tenant).stream().map(StatusDescriptorDto::new)
                .collect(Collectors.toList());
    }
}
