package net.pkhapps.semitarius.server.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Base interface for repositories. Spring Data JPA will take care of actually instantiating and implementing the
 * interfaces.
 */
public interface Repository<T extends AggregateRoot> extends JpaRepository<T, Long> {
}
