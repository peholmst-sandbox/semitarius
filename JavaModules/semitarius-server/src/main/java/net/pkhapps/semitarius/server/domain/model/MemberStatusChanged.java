package net.pkhapps.semitarius.server.domain.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Domain event published when the status of a {@link Member} is changed.
 *
 * @see MemberStatus
 */
public class MemberStatusChanged extends MemberEvent {

    private final Long newStatusDescriptorId;

    public MemberStatusChanged(@NotNull Long memberId, @NotNull Long newStatusDescriptorId) {
        super(memberId);
        this.newStatusDescriptorId =
                Objects.requireNonNull(newStatusDescriptorId, "newStatusDescriptorId must not be null");
    }

    @NotNull
    public Long getNewStatusDescriptorId() {
        return newStatusDescriptorId;
    }
}
