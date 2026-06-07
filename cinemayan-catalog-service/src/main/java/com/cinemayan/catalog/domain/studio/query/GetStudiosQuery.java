package com.cinemayan.catalog.domain.studio.query;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.persistence.GetStudiosParams;
import com.cinemayan.catalog.domain.studio.persistence.StudioStorage;
import com.cinemayan.core.domain.pagination.PaginatedResponse;
import com.cinemayan.core.domain.pagination.PaginationCriteria;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GetStudiosQuery {

    private final StudioStorage storage;

    public Output execute (Input input) {
        log.debug("Fetching studios by {}", input);
        PaginatedResponse<Studio> studios = storage.findAll(input.getParams(), input.getCriteria());
        log.debug("Studios found by input = {}, studios = {}", input, studios);
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
