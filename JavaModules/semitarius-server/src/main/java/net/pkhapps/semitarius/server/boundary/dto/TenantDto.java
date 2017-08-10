package net.pkhapps.semitarius.server.boundary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.pkhapps.semitarius.server.domain.Tenant;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * DTO for tenant information. Currently used for output only.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantDto {

    @JsonProperty
    public String identifier;

    @JsonProperty
    public String name;

    @JsonProperty
    public CoordinatesDto stationCoordinates;

    public TenantDto(@NotNull Tenant tenant) {
        Objects.requireNonNull(tenant, "tenant must not be null");
        identifier = tenant.getIdentifier();
        name = tenant.getName();
        tenant.getStationCoordinates().map(CoordinatesDto::new).ifPresent(dto -> stationCoordinates = dto);
    }
}
