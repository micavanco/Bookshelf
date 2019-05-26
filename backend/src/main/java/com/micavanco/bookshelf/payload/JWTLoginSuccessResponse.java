package com.micavanco.bookshelf.payload;

import static com.micavanco.bookshelf.configuration.SecurityConstants.TOKEN_EXPIRATION_TIME;

public class JWTLoginSuccessResponse {
    private boolean success;
    private String token;
    private Long   expires_at;
    private String username;

    public JWTLoginSuccessResponse(String username,boolean success, String token) {
        this.username = username;
        this.success = success;
        this.token = token;
        this.expires_at = TOKEN_EXPIRATION_TIME/60000;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Long expires_at) {
        this.expires_at = expires_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "JWTLoginSuccessResponse{" +
                "username="+username+
                ", expires_at="+expires_at+
                ", success=" + success +
                ", token='" + token + '\'' +
                '}';
    }
}
