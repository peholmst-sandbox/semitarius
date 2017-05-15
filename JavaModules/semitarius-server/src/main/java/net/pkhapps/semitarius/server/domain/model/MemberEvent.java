package net.pkhapps.semitarius.server.domain.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Base class for domain events that concern {@link Member}s.
 */
public abstract class MemberEvent {

    private final Long memberId;

    public MemberEvent(@NotNull Long memberId) {
        this.memberId = Objects.requireNonNull(memberId, "memberId must not be null");
    }

    @NotNull
    public Long getMemberId() {
        return memberId;
    }
}
