package net.pkhapps.semitarius.server.boundary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.pkhapps.semitarius.server.domain.model.Member;
import org.hibernate.validator.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * DTO for member information. Can be used both for input and output.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {

    @JsonProperty
    public Long id;

    @JsonProperty
    @NotEmpty // When used as input
    public String firstName;

    @JsonProperty
    @NotEmpty // When used as input
    public String lastName;

    @JsonProperty
    public String email;

    @JsonProperty
    public String phoneNumber;

    public MemberDto() {
    }

    public MemberDto(@NotNull Member member) {
        Objects.requireNonNull(member, "member must not be null");
        id = member.requireId();
        firstName = member.getFirstName();
        lastName = member.getLastName();
        member.getEmail().ifPresent(e -> email = e);
        member.getPhoneNumber().ifPresent(p -> phoneNumber = p);
    }
}
