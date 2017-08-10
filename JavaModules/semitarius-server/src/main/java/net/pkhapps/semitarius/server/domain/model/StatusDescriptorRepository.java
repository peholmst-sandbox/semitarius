package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.Repository;
import net.pkhapps.semitarius.server.domain.Tenant;

import java.util.List;
import java.util.Optional;

/**
 * Repository of {@link StatusDescriptor}s.
 */
public interface StatusDescriptorRepository extends Repository<StatusDescriptor> {

    Optional<StatusDescriptor> findByTenantAndId(Tenant tenant, Long id);

    List<StatusDescriptor> findByTenant(Tenant tenant);
}
