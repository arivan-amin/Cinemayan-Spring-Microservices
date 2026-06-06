package com.cinemayan.catalog.domain.studio.query;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.persistence.GetStudiosParams;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import com.cinemayan.core.domain.pagination.PaginatedResponse;
import com.cinemayan.core.domain.pagination.PaginationCriteria;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class GetStudiosQuery {

    private final StudioStorage storage;

    public Output execute (Input input) {
        PaginatedResponse<Studio> studios = storage.findAll(input.getParams(), input.getCriteria());
        return new Output(studios);
    }

    @Value
    public static class Input {

        GetStudiosParams params;
        PaginationCriteria criteria;
    }

    @Value
    public static class Output {

        PaginatedResponse<Studio> studios;
    }
}
