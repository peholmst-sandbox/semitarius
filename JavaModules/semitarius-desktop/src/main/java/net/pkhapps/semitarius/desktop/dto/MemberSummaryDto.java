package net.pkhapps.semitarius.desktop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberSummaryDto {

    @JsonProperty
    public MemberDto member;

    @JsonProperty
    public MemberStatusDto status;

    @JsonProperty
    public MemberLocationDto location;
}
