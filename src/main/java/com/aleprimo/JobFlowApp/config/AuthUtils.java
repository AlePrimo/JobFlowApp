package com.aleprimo.JobFlowApp.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class AuthUtils {

    public static String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("email"); // 👈 Ajustá el nombre del claim si es diferente
        }
        throw new RuntimeException("No se pudo obtener el email del usuario autenticado");
    }
}
