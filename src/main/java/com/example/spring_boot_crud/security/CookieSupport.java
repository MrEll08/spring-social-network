package com.example.spring_boot_crud.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieSupport {
    public CookieSupport(@Value("${app.auth.cookie-name}") String name,
            @Value("${app.auth.cookie-domain}") String domain, @Value("${app.auth.cookie-secure}") boolean secure,
            @Value("${app.auth.cookie-same-site}") String sameSite) {
        this.name = name;
        this.domain = domain;
        this.secure = secure;
        this.sameSite = sameSite;
    }

    private final String name, domain, sameSite;
    private final boolean secure;

    public String name() {
        return name;
    }

    public ResponseCookie buildAccessCookie(String token, long maxAgeSeconds) {
        return ResponseCookie.from(name, token).httpOnly(true).secure(secure).domain(domain).sameSite(sameSite)
                .path("/").maxAge(maxAgeSeconds).build();
    }

    public ResponseCookie clearAccessCookie() {
        return ResponseCookie.from(name, "").httpOnly(true).secure(secure).domain(domain).sameSite(sameSite).path("/")
                .maxAge(0).build();
    }
}
