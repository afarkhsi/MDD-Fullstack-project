package com.openclassrooms.mddapi.security.jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    // Injecte un service pour valider le token et charger l'utilisateur
    // Exemple : private JwtUtils jwtUtils; private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. Extraire JWT du header Authorization
            // 2. Valider JWT
            // 3. Charger UserDetails
            // 4. Créer Authentication token et le placer dans le contexte

            // Si pas de token ou invalide, ne rien faire

        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }
}
