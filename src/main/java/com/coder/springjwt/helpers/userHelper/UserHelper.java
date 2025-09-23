package com.coder.springjwt.helpers.userHelper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserHelper {

    public static Map<String, String> getCurrentUser() {

        HashMap<String,String> currentUser = new HashMap<>();

        // Get the Authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the username
        String username = authentication.getName();

        // Get roles/authorities
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Convert roles to a string for demonstration purposes
        StringBuilder roles = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            roles.append(authority.getAuthority()).append(" ");
        }
        currentUser.put("username",username);
        currentUser.put("roles",roles.toString().trim());

        return currentUser;
    }

}
