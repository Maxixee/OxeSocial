package com.oxesocial.ms_users.jwt;

public class JwtToken {

    public JwtToken() {
    }

    public JwtToken(String token) {
        this.token = token;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
