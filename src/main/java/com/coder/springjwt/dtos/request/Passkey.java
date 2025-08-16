package com.coder.springjwt.dtos.request;

import jakarta.validation.constraints.NotBlank;

public class Passkey {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank(message = "PassKey is Empty")
    private String passKey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    public Passkey(String username, String password, String passKey) {
        this.username = username;
        this.password = password;
        this.passKey = passKey;
    }

    public Passkey() {
    }
}
