package com.example.astrotalk.filter;

import com.example.astrotalk.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Set;

import static com.example.astrotalk.util.JwtAuthFilterConstants.*;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        final String authHeader = request.getHeader(AUTH_HEADER);
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null && (jwtService.isTokenValid(jwt, userDetails(userEmail)))) {
            UsernamePasswordAuthenticationToken authToken = usernamePasswordAuthenticationToken(jwt, userEmail);
            authToken.setDetails(authenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            log.info(
                    "User successfull: " + userEmail
            );

        }
        filterChain.doFilter(request, response);
    }

    private UserDetails userDetails(String userMail) {
        return this.userDetailsService.loadUserByUsername(userMail);
    }

    private WebAuthenticationDetailsSource authenticationDetailsSource() {
        return new WebAuthenticationDetailsSource();
    }

    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken(String token, String subject) {
        return new UsernamePasswordAuthenticationToken(
                userPrincipal(token, subject), "",
                simpleGrantedAuthoritySet(token)
        );
    }

    private Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet(String token) {
        log.info("{}", jwtService.simpleGrantedAuthorities(token));
        return jwtService.simpleGrantedAuthorities(token);
    }


    private User userPrincipal(String token, String subject) {
        return new User(subject, "", simpleGrantedAuthoritySet(token));
    }
}