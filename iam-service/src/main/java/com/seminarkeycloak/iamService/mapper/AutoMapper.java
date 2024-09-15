package com.seminarkeycloak.iamService.mapper;

import com.seminarkeycloak.iamService.dto.Role;
import com.seminarkeycloak.iamService.dto.User;
import com.seminarkeycloak.iamService.service.UserService;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface AutoMapper {
    default String getAttributes(UserRepresentation userRepresentation, String fieldName) {
        return UserService.getAttribute(userRepresentation, fieldName);
    }

    default List<CredentialRepresentation> mapCredentials(String password) {
        List<CredentialRepresentation> credentials = new ArrayList<>();
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setValue(password);

        credentials.add(credential);
        return credentials;
    }

    default Map<String, List<String>> mapAttributes(User user, @Context boolean isCreate) {
        boolean checkAttributes = isCreate || Objects.isNull(user.getAttributes());
        return checkAttributes ? user.initAttributes() : user.getAttributes();
    }

    @Mapping(target = "phoneNumber", expression = "java(getAttributes(userRepresentation, \"phoneNumber\"))")
    @Mapping(target = "address", expression = "java(getAttributes(userRepresentation, \"address\"))")
    User mapUser(UserRepresentation userRepresentation);

    List<User> mapUser(List<UserRepresentation> userRepresentation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", defaultValue = "true")
    @Mapping(target = "emailVerified", defaultValue = "false")
    @Mapping(target = "credentials", expression = "java(mapCredentials(user.getPassword()))")
    @Mapping(target = "attributes", expression = "java(mapAttributes(user, isCreate))")
    UserRepresentation mapUserRepresentation(User user, @Context boolean isCreate);

    @Mapping(target = "idClient", source = "containerId", defaultValue = "null")
    Role mapRole(RoleRepresentation roleRepresentation);

    List<Role> mapRole(List<RoleRepresentation> roleRepresentation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "containerId", source = "idClient", defaultValue = "null")
    @Mapping(target = "clientRole", expression = "java((role.getIdClient() != null && !role.getIdClient().isBlank()) ? true : false)")
    RoleRepresentation mapRoleRepresentation(Role role);
}
