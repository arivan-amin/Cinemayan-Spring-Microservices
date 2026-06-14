package com.cinemayan.core.domain.pagination;

import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class PaginationCriteria {

    public static final int MAX_SIZE = 50;

    @NotNull
    @PositiveOrZero
    int page;

    @NotNull
    @Positive
    @Max (MAX_SIZE)
    int size;

    String sortField;
    Direction sortDirection;

    public static PaginationCriteria of (int page, int size, String sortField,
                                         Direction sortDirection) {
        return new PaginationCriteria(page, size, sortField, sortDirection);
    }
}
