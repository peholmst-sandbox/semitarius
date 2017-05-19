package net.pkhapps.semitarius.server.domain.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Domain event published when the location of a {@link Member} is changed.
 *
 * @see MemberLocation
 */
public class MemberLocationChanged extends MemberEvent {

    private final Integer newDistanceToStation;

    public MemberLocationChanged(@NotNull Long memberId, @NotNull Integer newDistanceToStation) {
        super(memberId);
        this.newDistanceToStation =
                Objects.requireNonNull(newDistanceToStation, "newDistanceToStation must not be null");
    }

    @NotNull
    public Integer getNewDistanceToStation() {
        return newDistanceToStation;
    }
}
