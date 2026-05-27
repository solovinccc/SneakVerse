package com.vincenzolisi.sneakverseordersservice.Configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (jwtService.isTokenValid(token)) {
                    String email = jwtService.extractEmail(token);
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(email, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(auth);


                    filterChain.doFilter(
                            new HttpServletRequestWrapper(request) {
                                @Override
                                public String getHeader(String name) {
                                    if ("Authorization".equalsIgnoreCase(name)) return null;
                                    return super.getHeader(name);
                                }
                            },
                            response
                    );
                    return;
                }
            } catch (Exception ignored) {}
        }

        filterChain.doFilter(request, response);
    }
}