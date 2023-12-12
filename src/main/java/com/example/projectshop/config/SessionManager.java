package com.example.projectshop.config;

import com.example.projectshop.dto.auth.LoginRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionManager {

    private LoginRequest loginRequest;

    public LoginRequest getUserLogin() {
        return loginRequest;
    }

    public void setUserLogins(LoginRequest user) {
        this.loginRequest = user;
    }

}
