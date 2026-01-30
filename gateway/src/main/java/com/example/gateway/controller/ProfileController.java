package com.example.gateway.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProfileController {

    @GetMapping("/profile")
    public Map<String, Object> profile(@AuthenticationPrincipal OidcUser user) {
        if (user == null) return Map.of();
        return Map.of(
                "name", user.getFullName(),
                "email", user.getEmail()
        );
    }
}