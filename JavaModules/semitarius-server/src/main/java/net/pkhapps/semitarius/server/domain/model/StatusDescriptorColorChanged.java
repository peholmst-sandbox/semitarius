package net.pkhapps.semitarius.server.domain.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Domain event published when the color of a a {@link StatusDescriptor} is changed.
 */
public class StatusDescriptorColorChanged extends StatusDescriptorEvent {

    private final Integer newColor;

    StatusDescriptorColorChanged(@NotNull Long statusDescriptorId, @Nullable Integer newColor) {
        super(statusDescriptorId);
        this.newColor = newColor;
    }

    @Nullable
    public Integer getNewColor() {
        return newColor;
    }
}
