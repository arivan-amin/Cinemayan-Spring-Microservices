package com.cinemayan.catalog.application.config.cache;

import com.cinemayan.core.domain.pagination.PaginationCriteria;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
class DefaultCacheKeyGenerator implements KeyGenerator {

    public static final String NULL_VALUE = "null";
    public static final String SEPARATOR = ":";

    @Override
    public Object generate (Object target, Method method, Object... params) {
        return Arrays.stream(params)
            .map(this::resolveKey)
            .collect(Collectors.joining(SEPARATOR));
    }

    private String resolveKey (Object param) {
        if (param == null) {
            return NULL_VALUE;
        }
        if (param instanceof PaginationCriteria criteria) {
            return resolvePaginationParameter(criteria);
        }
        return String.valueOf(param);
    }

    private String resolvePaginationParameter (PaginationCriteria criteria) {
        return String.join(SEPARATOR, String.valueOf(criteria.getPage()),
            String.valueOf(criteria.getSize()),
            StringUtils.hasText(criteria.getSortField()) ? criteria.getSortField() : NULL_VALUE,
            criteria.getSortDirection() != null ? criteria.getSortDirection()
                .name() : NULL_VALUE);
    }
}
