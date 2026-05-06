package com.cinemayan.apigateway.application.config;

import com.cinemayan.apigateway.application.routing.DefaultRouteCreator;
import com.cinemayan.apigateway.domain.RouteCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import reactor.core.publisher.Mono;

import static java.util.Objects.requireNonNull;

@Configuration
@RequiredArgsConstructor
class RoutingBeansConfig {

    public static final String REDIS_HOST_VARIABLE = "REDIS_HOST";
    public static final String REDIS_PORT_VARIABLE = "REDIS_PORT";
    public static final String REDIS_DEFAULT_HOST = "localhost";
    public static final int REDIS_DEFAULT_PORT = 6379;

    private final Environment environment;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory () {
        String host = environment.getProperty(REDIS_HOST_VARIABLE, REDIS_DEFAULT_HOST);
        int port = environment.getProperty(REDIS_PORT_VARIABLE, Integer.class, REDIS_DEFAULT_PORT);
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisRateLimiter rateLimiter () {
        return new RedisRateLimiter(1, 1);
    }

    @Bean
    public KeyResolver keyResolver () {
        // todo 1/31/25 - later when security is added, keyResolver should depend on user ID
        return exchange -> Mono.just(requireNonNull(exchange.getRequest()
            .getRemoteAddress()).getAddress()
            .getHostAddress());
    }

    @Bean
    public RouteCreator routeCreator (RedisRateLimiter rateLimiter, KeyResolver keyResolver) {
        return new DefaultRouteCreator(rateLimiter, keyResolver);
    }
}
