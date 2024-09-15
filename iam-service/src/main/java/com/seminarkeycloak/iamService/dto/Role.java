package com.seminarkeycloak.iamService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("idClient")
    private String idClient;

    @JsonIgnore
    private Boolean clientRole;
}
