package net.pkhapps.semitarius.server.boundary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.pkhapps.semitarius.server.domain.model.MemberStatus;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Objects;

/**
 * DTO for member status. Can be used both for input and output.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberStatusDto {

    @JsonProperty
    @javax.validation.constraints.NotNull // When used as input
    public Long statusDescriptorId;

    @JsonProperty
    public Instant changedOn;

    public MemberStatusDto() {
    }

    public MemberStatusDto(@NotNull MemberStatus memberStatus) {
        Objects.requireNonNull(memberStatus, "memberStatus must not be null");
        memberStatus.getStatus().ifPresent(status -> statusDescriptorId = status.requireId());
        changedOn = memberStatus.getChangedOn();
    }
}
