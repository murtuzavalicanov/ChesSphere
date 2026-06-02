package com.chessphere.gateway.bean;

import com.chessphere.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private JwtUtil jwtUtil;

    // SecurityConfig-in jwtUtil-i bura set edə bilməsi üçün setter metodu:
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private static final List<String> EXCLUDED_URLS = List.of(
            "/user/auth/",
            "/user/v3/api-docs",
            "/aggregate/",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/webjars"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        log.info("AuthenticationFilter filtering.path:"+exchange.getRequest().getPath());

        ServerHttpRequest request = exchange.getRequest();

        String path = request.getURI().getPath();

        boolean isExcluded =
                EXCLUDED_URLS.stream().anyMatch(path::contains);

        if (isExcluded) {
            log.info("Excluded path:"+path);
            return chain.filter(exchange);
        }

        String authHeader =
                request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(
                    exchange,
                    "Authorization header tapılmadı",
                    HttpStatus.UNAUTHORIZED
            );
        }

        try {

            String token = authHeader.substring(7);

            Claims claims = jwtUtil.getClaims(token);

            String username = claims.getSubject();

            String userId = claims.get("userId", String.class);
            log.info("AuthenticationFilter userId: {}", userId);

            List<String> roles =
                    claims.get("roles", List.class);

            List<SimpleGrantedAuthority> authorities =
                    roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );

            ServerHttpRequest modifiedRequest =
                    request.mutate()
                            .header("X-User-Id", userId)
                            .header("User-Name", username)
                            .header("X-Roles", String.join(",", roles))
                            .build();

            return chain.filter(
                            exchange.mutate()
                                    .request(modifiedRequest)
                                    .build()
                    )
                    .contextWrite(
                            ReactiveSecurityContextHolder
                                    .withAuthentication(authentication)
                    );

        } catch (Exception e) {

            return onError(
                    exchange,
                    "Token keçərsizdir: " + e.getMessage(),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}