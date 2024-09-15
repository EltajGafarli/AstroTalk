package com.example.astrotalk.security;

import com.example.astrotalk.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFiler;

    @Bean
    @SuppressWarnings("all")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(
                        //AUTH
                        request -> request
                                .requestMatchers(AUTH_WHITELIST)
                                .permitAll()
                                .requestMatchers("/api/auth/login", "/api/auth/register")
                                .permitAll()
                                .requestMatchers("/api/auth/{userName}")
                                .authenticated()
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/user", "/api/user/**")
                                .authenticated()
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/user-details/**")
                                .authenticated()
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.POST,"/api/planet")
                                .hasAnyAuthority("ADMIN")
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.GET,"/api/planet")
                                .authenticated()
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.PUT, "/api/planet/{planetId}")
                                .hasAnyAuthority("ADMIN")
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.GET,"/api/horoscope")
                                .authenticated()
                                .requestMatchers(HttpMethod.GET,"/api/horoscope/name/{name}")
                                .authenticated()
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/horoscope/**")
                                .hasAuthority("ADMIN")
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.GET, "/api/planet/{planetId}")
                                .authenticated()
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.DELETE, "/api/planet/{planetId}")
                                .hasAnyAuthority("ADMIN")
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.GET, "/api/horoscope-details/{hId}")
                                .authenticated()
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.POST, "api/horoscope-details/{hId}")
                                .hasAuthority("ADMIN")
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/horoscope-details/{hId}/**")
                                .authenticated()
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/follow/{userToFollowId}", "/unfollow/{userToUnfollowId}", "/followersCount", "/followingCount", "/recommendations")
                                .authenticated()
                )

                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/files/**")
                                .authenticated()
                )
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.POST, "/api/motivation")
                                .hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/motivation/{id}")
                                .hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/motivation/{id}")
                                .hasAnyAuthority("ADMIN")
                                .requestMatchers("/api/motivation/**")
                                .authenticated()
                )
                .logout(
                        request -> request.
                                logoutUrl("/api/auth/logout")
                                .logoutSuccessHandler(
                                        new HttpStatusReturningLogoutSuccessHandler()
                                ).logoutSuccessUrl("/")
                )
                .sessionManagement()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFiler, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
