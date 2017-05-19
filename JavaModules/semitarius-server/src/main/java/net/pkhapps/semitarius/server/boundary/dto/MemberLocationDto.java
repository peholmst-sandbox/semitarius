package net.pkhapps.semitarius.server.boundary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.pkhapps.semitarius.server.domain.model.MemberLocation;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Objects;

/**
 * DTO for member location. Can be used both for input and output.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberLocationDto {

    @JsonProperty
    public Instant changedOn;

    @JsonProperty
    @javax.validation.constraints.NotNull // When used as input
    public Integer distanceToStation;

    public MemberLocationDto() {
    }

    public MemberLocationDto(@NotNull MemberLocation memberLocation) {
        Objects.requireNonNull(memberLocation, "memberLocation must not be null");
        changedOn = memberLocation.getChangedOn();
        memberLocation.getDistanceToStation().ifPresent(d -> distanceToStation = d);
    }
}
