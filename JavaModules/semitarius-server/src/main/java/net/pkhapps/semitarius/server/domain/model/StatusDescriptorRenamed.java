package net.pkhapps.semitarius.server.domain.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Domain event published when a {@link StatusDescriptor} is renamed.
 */
public class StatusDescriptorRenamed extends StatusDescriptorEvent {

    private final String newName;

    StatusDescriptorRenamed(@NotNull Long statusDescriptorId, @NotNull String newName) {
        super(statusDescriptorId);
        this.newName = Objects.requireNonNull(newName, "newName must not be null");
    }

    @NotNull
    public String getNewName() {
        return newName;
    }
}
