package net.pkhapps.semitarius.server.boundary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

/**
 * DTO that combines member information, status and location into a single structure. Currently used for output only.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberSummaryDto {

    @JsonProperty
    public MemberDto member;

    @JsonProperty
    public MemberStatusDto status;

    @JsonProperty
    public MemberLocationDto location;

    public MemberSummaryDto() {
    }

    public MemberSummaryDto(@Nullable MemberDto member, @Nullable MemberStatusDto status,
                            @Nullable MemberLocationDto location) {
        this.member = member;
        this.status = status;
        this.location = location;
    }
}
