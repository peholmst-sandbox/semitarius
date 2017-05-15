package net.pkhapps.semitarius.server.domain.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Base class for domain events that concern {@link StatusDescriptor}s.
 */
public abstract class StatusDescriptorEvent {

    private final Long statusDescriptorId;

    StatusDescriptorEvent(@NotNull Long statusDescriptorId) {
        this.statusDescriptorId = Objects.requireNonNull(statusDescriptorId, "statusDescriptorId must not be null");
    }

    @NotNull
    public Long getStatusDescriptorId() {
        return statusDescriptorId;
    }
}