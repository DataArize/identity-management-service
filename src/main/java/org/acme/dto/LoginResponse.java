package org.acme.dto;

import lombok.Data;
import org.keycloak.representations.AccessTokenResponse;

import java.io.Serializable;

@Data
public class LoginResponse implements Serializable {

    private String token;
    private long expiresIn;
    private long refreshExpiresIn;
    private String refreshToken;
    private String tokenType;
    private String idToken;

    public LoginResponse(AccessTokenResponse accessTokenResponse) {
        this.token = accessTokenResponse.getToken();
        this.expiresIn = accessTokenResponse.getExpiresIn();
        this.refreshExpiresIn = accessTokenResponse.getRefreshExpiresIn();
        this.refreshToken = accessTokenResponse.getRefreshToken();
        this.tokenType = accessTokenResponse.getTokenType();
        this.idToken = accessTokenResponse.getIdToken();
    }
}
