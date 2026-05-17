package com.cinemayan.apigateway.application.config;

import com.cinemayan.apigateway.application.routing.DefaultRouteCreator;
import com.cinemayan.apigateway.domain.RouteCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import reactor.core.publisher.Mono;

import static java.util.Objects.requireNonNull;

@Configuration
@RequiredArgsConstructor
@Slf4j
class RoutingBeansConfig {

    private final RedisProperties redis;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory () {
        log.info("Redis rate limiter properties = {}", redis);
        return new LettuceConnectionFactory(redis.host(), redis.port());
    }

    @Bean
    public RedisRateLimiter rateLimiter () {
        return new RedisRateLimiter(1, 1);
    }

    @Bean
    public KeyResolver keyResolver () {
        return exchange -> Mono.just(requireNonNull(exchange.getRequest()
            .getRemoteAddress()).getAddress()
            .getHostAddress());
    }

    @Bean
    public RouteCreator routeCreator (RedisRateLimiter rateLimiter, KeyResolver keyResolver) {
        return new DefaultRouteCreator(rateLimiter, keyResolver);
    }
}
