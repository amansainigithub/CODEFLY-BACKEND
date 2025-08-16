package com.coder.springjwt.dtos.customerPayloads.customerPayload;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

@Component
public class CustomerLoginPayload {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String userrole;

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

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    @Override
    public String toString() {
        return "CustomerLoginPayload{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userrole='" + userrole + '\'' +
                '}';
    }

    public CustomerLoginPayload(String username, String password, String userrole) {
        this.username = username;
        this.password = password;
        this.userrole = userrole;
    }

    public CustomerLoginPayload() {
    }
}
