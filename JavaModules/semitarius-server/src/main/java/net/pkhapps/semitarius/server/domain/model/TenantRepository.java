package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.Repository;

import java.util.Optional;

/**
 * Repository of {@link Tenant}s.
 */
public interface TenantRepository extends Repository<Tenant> {

    Optional<Tenant> findByIdentifier(String identifier);
}
