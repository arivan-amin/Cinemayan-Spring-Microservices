package com.cinemayan.core.domain.pagination;

import java.util.List;

public record PaginatedResponse<T>(
    PageData pageData,
    List<T> content) {

    public static <T> PaginatedResponse<T> of (PageData pageData, List<T> content) {
        return new PaginatedResponse<>(pageData, content);
    }
}
