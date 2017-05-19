package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.AggregateRoot;
import net.pkhapps.semitarius.server.domain.ConstructorUsedByJPAOnly;
import net.pkhapps.semitarius.server.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Optional;

/**
 * Aggregate root representing a tenant in the system. Tenants are completely isolated from each other. For fire
 * departments, each station (or even fire department) would be its own tenant.
 */
@Entity
@Table(name = Tenant.TABLE_NAME)
public class Tenant extends AggregateRoot {

    static final String TABLE_NAME = "tenants";
    static final String COL_IDENTIFIER = "identifier";
    static final String COL_NAME = "name";
    static final String COL_STATION_LAT = "station_lat";
    static final String COL_STATION_LON = "station_lon";

    @Column(unique = true, nullable = false, name = COL_IDENTIFIER)
    private String identifier;

    @Column(nullable = false, name = COL_NAME)
    private String name;

    @Column(name = COL_STATION_LAT)
    private Double stationLatitude;

    @Column(name = COL_STATION_LON)
    private Double stationLongitude;

    @ConstructorUsedByJPAOnly
    @SuppressWarnings("unused")
    Tenant() {
    }

    public Tenant(@NotNull String identifier, @NotNull String name) {
        this.identifier = Strings.requireNonEmpty(identifier, "identifier must not be empty");
        this.name = Strings.requireNonEmpty(name, "name must not be empty");
    }

    /**
     * Returns the unique textual identifier of the tenant. This is the preferred way of identifying tenants since
     * textual IDs are easier to remember than numerical IDs.
     */
    @NotNull
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the human-friendly name of the tenant.
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Returns the coordinates of the (fire) station.
     */
    @NotNull
    public Optional<Coordinates> getStationCoordinates() {
        if (stationLatitude == null || stationLongitude == null) {
            return Optional.empty();
        } else {
            return Optional.of(new Coordinates(stationLatitude, stationLongitude));
        }
    }

    public void setStationCoordinates(@Nullable Coordinates coordinates) {
        // TODO Maybe fire a domain event?
        if (coordinates == null) {
            stationLatitude = null;
            stationLongitude = null;
        } else {
            stationLatitude = coordinates.getLatitude();
            stationLongitude = coordinates.getLongitude();
        }
    }
}
