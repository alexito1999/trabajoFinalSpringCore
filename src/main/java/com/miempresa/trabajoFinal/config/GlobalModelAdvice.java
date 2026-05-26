package com.miempresa.trabajoFinal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {

    @Autowired
    private Environment env;

    @ModelAttribute("activeProfile")
    public String activeProfile() {
        try {
            String[] profiles = env.getActiveProfiles();
            if (profiles.length == 0) return "dev";
            return profiles[0];
        } catch (Exception e) {
            return "dev";
        }
    }

    @ModelAttribute("autenticado")
    public boolean autenticado() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
        } catch (Exception e) {
            return false;
        }
    }

    @ModelAttribute("username")
    public String username() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
                return auth.getName();
            }
        } catch (Exception e) {
        }
        return "";
    }

    @ModelAttribute("esAdmin")
    public boolean esAdmin() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
                return auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch("ROLE_ADMIN"::equals);
            }
        } catch (Exception e) {
        }
        return false;
    }
}
