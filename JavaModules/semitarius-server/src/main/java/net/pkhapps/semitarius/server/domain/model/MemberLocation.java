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
 * Aggregate root that keeps track of the location of a {@link Member}. Reporting the location is an optional feature
 * that the member can enable or disable. Also, for privacy reasons, the actual location is not recorded. Instead,
 * the distance to the fire station (in meters) is recorded. For the same reason, no history is stored that could be
 * used to track how the member is moving.
 */
@Entity
@Table(name = MemberLocation.TABLE_NAME)
public class MemberLocation extends AggregateRoot {

    static final String TABLE_NAME = "member_location";
    static final String COL_MEMBER = "member_id";
    static final String COL_DISTANCE = "distance";
    static final String COL_CHANGED_ON = "changed_on";

    @OneToOne(optional = false)
    @JoinColumn(unique = true, nullable = false, name = COL_MEMBER)
    private Member member;

    @Column(name = COL_DISTANCE)
    private Integer distanceToStation;

    @Column(nullable = false, name = COL_CHANGED_ON)
    private Instant changedOn;

    @ConstructorUsedByJPAOnly
    @SuppressWarnings("unused")
    MemberLocation() {
    }

    public MemberLocation(@NotNull Member member, @NotNull Clock clock) {
        this.member = Objects.requireNonNull(member, "member must not be null");
        this.changedOn = Objects.requireNonNull(clock, "clock must not be null").instant();
    }

    @NotNull
    public Member getMember() {
        return member;
    }

    /**
     * Returns the distance in meters of the member to the fire station.
     */
    @NotNull
    public Optional<Integer> getDistanceToStation() {
        return Optional.ofNullable(distanceToStation);
    }

    @NotNull
    public Instant getChangedOn() {
        return changedOn;
    }

    /**
     * Changes the distance to the station (in meters) of this member, firing a {@link MemberLocationChanged} domain
     * event.
     */
    public void changeDistance(int distanceToStation, @NotNull Clock clock) {
        Objects.requireNonNull(clock, "clock must not be null");
        if (!Objects.equals(this.distanceToStation, distanceToStation)) {
            registerEvent(new MemberLocationChanged(member.requireId(), distanceToStation));
            this.distanceToStation = distanceToStation;
            this.changedOn = clock.instant();
        }
    }
}
