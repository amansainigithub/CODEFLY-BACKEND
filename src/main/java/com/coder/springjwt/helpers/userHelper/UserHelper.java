package com.coder.springjwt.helpers.userHelper;

import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.User;
import com.coder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserHelper {

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> getCurrentUser() {

        HashMap<String,String> currentUser = new HashMap<>();

        // Get the Authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the username
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new DataNotFoundException("User Not Found... :: " + UserHelper.class.getName()));

        // Get roles/authorities
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Convert roles to a string for demonstration purposes
        StringBuilder roles = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            roles.append(authority.getAuthority()).append(" ");
        }
        currentUser.put("username",username);
        currentUser.put("userId",String.valueOf(user.getId()));
        currentUser.put("roles",roles.toString().trim());

        return currentUser;
    }


}
