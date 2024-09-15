package com.seminarkeycloak.iamService.service;

import com.seminarkeycloak.iamService.dto.User;
import com.seminarkeycloak.iamService.dto.UserSearch;
import org.keycloak.representations.idm.MappingsRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserService {
    User create(User user);

    User update(Jwt jwt, User user);

    void forgotPassword(String username);

    void delete(String userId);

    List<User> search(UserSearch search);

    MappingsRepresentation getRolesUser(String userId);

    User getCurrentLogin(Jwt jwt);

    static List<String> getAttributes(UserRepresentation userRepresentation, String fieldName) {
        return Optional.ofNullable(userRepresentation.getAttributes())
                .map(attributes -> attributes.computeIfAbsent(fieldName, k -> new ArrayList<>()))
                .orElse(null);
    }

    static String getAttribute(UserRepresentation userRepresentation, String fieldName) {
        List<String> values = getAttributes(userRepresentation, fieldName);
        if (Objects.isNull(values)) return null;

        return values.get(0);
    }
}
