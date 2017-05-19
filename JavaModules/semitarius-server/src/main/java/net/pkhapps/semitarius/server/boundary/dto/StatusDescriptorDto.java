package net.pkhapps.semitarius.server.boundary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.pkhapps.semitarius.server.domain.model.StatusDescriptor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * DTO for status descriptor information. Currently used for output only.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDescriptorDto {

    @JsonProperty
    public Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private Integer color;

    public StatusDescriptorDto(@NotNull StatusDescriptor statusDescriptor) {
        Objects.requireNonNull(statusDescriptor, "statusDescriptor must not be null");
        id = statusDescriptor.requireId();
        name = statusDescriptor.getName();
        color = statusDescriptor.getColor();
    }
}
