package com.seminarkeycloak.iamService.service.impl;

import com.seminarkeycloak.iamService.config.KeycloakConfig;
import com.seminarkeycloak.iamService.dto.User;
import com.seminarkeycloak.iamService.dto.UserSearch;
import com.seminarkeycloak.iamService.mapper.AutoMapper;
import com.seminarkeycloak.iamService.service.UserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.MappingsRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.keycloak.representations.IDToken.PREFERRED_USERNAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final KeycloakConfig keycloakConfig;
    private final AutoMapper autoMapper;

    private Optional<UserRepresentation> getByUsername(String username) {
        return keycloakConfig.getUsers().searchByUsername(username, true).stream().findFirst();
    }

    public void getSendVerifyEmail(String id) {
        log.info("Send verify email - id: {}", id);
        keycloakConfig.getUsers().get(id).sendVerifyEmail();
    }

    public void scheduleEmailSend(String id, Long seconds) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> getSendVerifyEmail(id), seconds, TimeUnit.SECONDS);
        scheduler.shutdown();
    }

    @Override
    public User create(User user) {
        try (Response response = keycloakConfig.getUsers().create(autoMapper.mapUserRepresentation(user, true))) {
            if (!response.getStatusInfo().equals(Response.Status.CREATED)) {
                log.error("Create error");
                return null;
            }

            this.getByUsername(user.getUsername())
                    .filter(u -> !u.isEmailVerified())
                    .ifPresent(u -> scheduleEmailSend(u.getId(), 3L));
        }
        return user;
    }

    @Override
    public User update(Jwt jwt, User user) {
        String userId = jwt.getSubject();

        Optional.ofNullable(userId)
                .ifPresentOrElse(
                        id -> {
                            UserRepresentation mapped = autoMapper.mapUserRepresentation(user, false);
                            keycloakConfig.getUsers().get(userId).update(mapped);
                        },
                        () -> {
                            throw new RuntimeException("Role ID not found");
                        }
                );
        return user;
    }

    @Override
    public void forgotPassword(String username) {
        this.getByUsername(username)
                .ifPresentOrElse(userRepresentation -> {
                    keycloakConfig.getUsers()
                            .get(userRepresentation.getId())
                            .executeActionsEmail(List.of("UPDATE_PASSWORD"));
                    log.debug("forgotPassword userID: {}", userRepresentation.getId());
                }, () -> {
                    throw new RuntimeException("User not found");
                });
    }

    @Override
    public User getCurrentLogin(Jwt jwt) {
        String username = jwt.getClaim(PREFERRED_USERNAME);
        log.info("Get me - username: {}", username);
        String id = jwt.getSubject();
        return autoMapper.mapUser(keycloakConfig.getUsers().get(id).toRepresentation());
    }

    @Override
    public void delete(String userId) {
        keycloakConfig.getUsers().delete(userId);
    }

    @Override
    public List<User> search(UserSearch search) {
        return autoMapper.mapUser(
                keycloakConfig.getUsers().search(
                        search.getUsername(),
                        search.getFirstName(),
                        search.getLastName(),
                        search.getEmail(),
                        search.getPageNumber(),
                        search.getPageSize()
                )
        );
    }

    @Override
    public MappingsRepresentation getRolesUser(String userId) {
        return keycloakConfig.getUsers().get(userId).roles().getAll();
    }
}
