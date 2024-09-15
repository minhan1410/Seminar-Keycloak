package com.seminarkeycloak.iamService.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakConfig {
    @Value("${keycloak.admin.username}")
    private String username;
    @Value("${keycloak.admin.password}")
    private String password;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.idClient}")
    private String idClient;
    @Value("${keycloak.clientSecret}")
    private String clientSecret;
    @Value("${keycloak.serverUrl}")
    private String serverUrl;

    public Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType("password")
                .username(username)
                .password(password)
                .build();
    }

    public RealmResource getRealm() {
        return this.getKeycloakInstance().realm(realm);
    }

    public ClientsResource getClients() {
        return this.getRealm().clients();
    }

    public ClientResource getClientIdDefault() {
        return this.getClients().get(idClient);
    }

    public RolesResource getRealmRoles() {
        return this.getRealm().roles();
    }

    public RolesResource getRolesInClientIdDefault() {
        return this.getClientIdDefault().roles();
    }

    public UsersResource getUsers() {
        return this.getRealm().users();
    }

    public void close() {
        this.getKeycloakInstance().close();
    }
}
