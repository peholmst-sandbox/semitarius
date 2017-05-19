package net.pkhapps.semitarius.desktop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberLocationDto {

    @JsonProperty
    public Instant changedOn;

    @JsonProperty
    public Integer distanceToStation;
}
