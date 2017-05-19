package net.pkhapps.semitarius.desktop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberStatusDto {

    @JsonProperty
    public Long statusDescriptorId;

    @JsonProperty
    public Instant changedOn;
}
