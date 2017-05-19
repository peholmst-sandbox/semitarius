package net.pkhapps.semitarius.server.boundary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.pkhapps.semitarius.server.domain.model.Coordinates;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * DTO for a pair of WGS-84 coordinates. Currently used for output only.
 */
public class CoordinatesDto {

    @JsonProperty
    public double latitude;

    @JsonProperty
    public double longitude;

    public CoordinatesDto(@NotNull Coordinates coordinates) {
        Objects.requireNonNull(coordinates, "coordinates must not be null");
        latitude = coordinates.getLatitude();
        longitude = coordinates.getLongitude();
    }
}
