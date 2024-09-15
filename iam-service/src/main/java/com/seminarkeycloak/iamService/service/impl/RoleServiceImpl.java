package com.seminarkeycloak.iamService.service.impl;

import com.seminarkeycloak.iamService.config.KeycloakConfig;
import com.seminarkeycloak.iamService.dto.Role;
import com.seminarkeycloak.iamService.dto.RoleSearch;
import com.seminarkeycloak.iamService.mapper.AutoMapper;
import com.seminarkeycloak.iamService.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.MappingsRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final KeycloakConfig keycloakConfig;
    private final AutoMapper autoMapper;

    @Override
    public Role create(Role role) {
        RoleRepresentation roleRepresentation = autoMapper.mapRoleRepresentation(role);
        if (StringUtils.hasText(role.getIdClient())) {
            keycloakConfig.getRolesInClientIdDefault().create(roleRepresentation);
        } else {
            keycloakConfig.getRealmRoles().create(roleRepresentation);
        }
        return role;
    }

    @Override
    public Role update(Role role) {
        Optional.ofNullable(role.getId())
                .ifPresentOrElse(
                        id -> keycloakConfig.getRolesInClientIdDefault().get(id).update(autoMapper.mapRoleRepresentation(role)),
                        () -> {
                            throw new RuntimeException("Role ID not found");
                        }
                );
        return role;
    }

    @Override
    public void delete(String roleName, Boolean isClient) {
        if (isClient) {
            keycloakConfig.getRolesInClientIdDefault().deleteRole(roleName);
        } else {
            keycloakConfig.getRealmRoles().deleteRole(roleName);
        }
    }

    @Override
    public List<Role> search(RoleSearch search) {
        return autoMapper.mapRole(this.filterRole(search));
    }

    private List<RoleRepresentation> filterRole(RoleSearch search) {
        ClientsResource clients = keycloakConfig.getClients();

        Map<String, List<RoleRepresentation>> mapClientRole = (search.getClientRole() == null || search.getClientRole())
                ? clients.findAll().stream()
                .collect(Collectors.toMap(
                        ClientRepresentation::getClientId,
                        client -> clients.get(client.getId()).roles().list())
                )
                : new HashMap<>();
        mapClientRole.put("realm-role", keycloakConfig.getRealmRoles().list());

        return mapClientRole.values().stream()
                .flatMap(java.util.Collection::stream)
                .filter(role -> search.getClientRole() == null || role.getClientRole().equals(search.getClientRole()))
                .filter(role -> !StringUtils.hasText(search.getId()) || search.getId().equals(role.getId()))
                .filter(role -> !StringUtils.hasText(search.getName()) || search.getName().equals(role.getName()))
                .toList();
    }

    @Override
    public MappingsRepresentation assignRoleToUser(String userId, String roleId) {
        RoleMappingResource roles = keycloakConfig.getUsers().get(userId).roles();
        roles.realmLevel().add(this.filterRole(RoleSearch.builder().id(roleId).build()));
        return roles.getAll();
    }

    @Override
    public MappingsRepresentation removeRoleFromUser(String userId, String roleId) {
        RoleMappingResource roles = keycloakConfig.getUsers().get(userId).roles();
        roles.realmLevel().remove(this.filterRole(RoleSearch.builder().id(roleId).build()));
        return roles.getAll();
    }
}
