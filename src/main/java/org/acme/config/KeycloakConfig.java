package org.acme.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

@ApplicationScoped
public class KeycloakConfig {

    @ConfigProperty(name = "quarkus.keycloak.admin-client.server-url")
    private String serverUrl;
    @ConfigProperty(name = "quarkus.keycloak.admin-client.realm")
    private String realm;
    @ConfigProperty(name = "quarkus.oidc.client-id")
    private String clientId;
    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    private String clientSecret;
    @ConfigProperty(name = "quarkus.keycloak.admin-client.username")
    private String username;
    @ConfigProperty(name = "quarkus.keycloak.admin-client.password")
    private String password;


    @Produces
    @Named("keycloakClient")
    public Keycloak keycloakClient() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .build();
    }

    @Produces
    @Named("keycloakClientBuilder")
    public KeycloakBuilder keyCloakConfig() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret);

    }

}
