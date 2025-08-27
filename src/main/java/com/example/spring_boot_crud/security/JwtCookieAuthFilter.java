package com.example.spring_boot_crud.security;

import com.example.spring_boot_crud.datamodel.UserRepository;
import com.example.spring_boot_crud.service.TokenService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtCookieAuthFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final CookieSupport cookies;
    private final UserRepository users;

    public JwtCookieAuthFilter(TokenService tokenService, CookieSupport cookies, UserRepository users) {
        this.tokenService = tokenService;
        this.cookies = cookies;
        this.users = users;
    }

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        String token = null;
        if (req.getCookies() != null) {
            for (var c : req.getCookies()) {
                if (cookies.name().equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                var userId = tokenService.subjectUserId(token);
                var userOpt = users.findById(userId);
                if (userOpt.isPresent()) {
                    var auth = new UsernamePasswordAuthenticationToken(userOpt.get().getId(), null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (JwtException ignored) {
            }
        }
        chain.doFilter(req, resp);
    }
}
