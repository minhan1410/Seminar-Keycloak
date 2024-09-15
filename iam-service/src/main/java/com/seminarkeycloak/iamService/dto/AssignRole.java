package com.seminarkeycloak.iamService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignRole {
    private String userId;
    private RoleSearch roleSearch;
}
