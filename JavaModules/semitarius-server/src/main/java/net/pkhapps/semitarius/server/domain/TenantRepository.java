package net.pkhapps.semitarius.server.domain;

import java.util.Optional;

/**
 * Repository of {@link Tenant}s.
 */
public interface TenantRepository extends Repository<Tenant> {

    Optional<Tenant> findByIdentifier(String identifier);
}
