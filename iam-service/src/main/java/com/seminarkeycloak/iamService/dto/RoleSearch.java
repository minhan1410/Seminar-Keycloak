package com.seminarkeycloak.iamService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleSearch {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    protected String name;

    @JsonProperty(namespace = "clientRole")
    private Boolean clientRole;
}
