package com.qaitsolutions.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecialityDto {

    @JsonProperty
    private Integer id;

    @JsonProperty
    private String name;
}