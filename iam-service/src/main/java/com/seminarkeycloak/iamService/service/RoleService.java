package com.seminarkeycloak.iamService.service;

import com.seminarkeycloak.iamService.dto.Role;
import com.seminarkeycloak.iamService.dto.RoleSearch;
import org.keycloak.representations.idm.MappingsRepresentation;

import java.util.List;

public interface RoleService {
    Role create(Role role);

    Role update(Role role);

    void delete(String roleName, Boolean isClient);

    List<Role> search(RoleSearch search);

    MappingsRepresentation assignRoleToUser(String userId, String roleId);

    MappingsRepresentation removeRoleFromUser(String userId, String roleId);
}
