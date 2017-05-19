package net.pkhapps.semitarius.desktop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDescriptorDto {

    @JsonProperty
    public Long id;

    @JsonProperty
    public String name;

    @JsonProperty
    public Integer color;
}
