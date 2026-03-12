package com.chessphere.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                                .anyExchange()
//                                .pathMatchers(
//                            "/swagger-ui/**",
//                            "/v3/api-docs/**",
//                            "/swagger-ui.html"
//                    )
                .permitAll()
//                        .pathMatchers("/user-service/auth/**").permitAll()
//                        .anyExchange().authenticated() // Digər hər şey üçün giriş vacibdir
//                )
                );

        return http.build();
    }
}

