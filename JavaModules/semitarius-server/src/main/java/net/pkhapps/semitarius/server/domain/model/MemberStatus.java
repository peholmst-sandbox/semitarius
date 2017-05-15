package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.AggregateRoot;
import net.pkhapps.semitarius.server.domain.ConstructorUsedByJPAOnly;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * Aggregate root that keeps track of the current status of a {@link Member}. Since the status is expected to change
 * more often than the member information, it is kept in a separate class.
 */
@Entity
@Table(name = MemberStatus.TABLE_NAME)
public class MemberStatus extends AggregateRoot {

    static final String TABLE_NAME = "user_status";
    static final String COL_MEMBER = "member_id";
    static final String COL_STATUS = "status_descriptor_id";
    static final String COL_CHANGED_ON = "changed_on";

    @OneToOne(optional = false)
    @JoinColumn(unique = true, nullable = false, name = COL_MEMBER)
    private Member member;

    @ManyToOne
    @JoinColumn(name = COL_STATUS)
    private StatusDescriptor status;

    @Column(nullable = false, name = COL_CHANGED_ON)
    private Instant changedOn;

    @ConstructorUsedByJPAOnly
    @SuppressWarnings("unused")
    MemberStatus() {
    }

    public MemberStatus(@NotNull Member member, @NotNull Clock clock) {
        this.member = Objects.requireNonNull(member, "member must not be null");
        this.changedOn = Objects.requireNonNull(clock, "clock must not be null").instant();
    }

    @NotNull
    public Member getUser() {
        return member;
    }

    @NotNull
    public Optional<StatusDescriptor> getStatus() {
        return Optional.ofNullable(status);
    }

    @NotNull
    public Instant getChangedOn() {
        return changedOn;
    }

    /**
     * Changes the status of this member, firing a {@link MemberStatusChanged} domain event.
     */
    public void changeStatus(@NotNull StatusDescriptor newStatus, @NotNull Clock clock) {
        Objects.requireNonNull(newStatus, "newStatus must not be null");
        Objects.requireNonNull(clock, "clock must not be null");
        registerEvent(new MemberStatusChanged(member.requireId(), status.requireId()));
        this.status = newStatus;
        this.changedOn = clock.instant();
    }
}
