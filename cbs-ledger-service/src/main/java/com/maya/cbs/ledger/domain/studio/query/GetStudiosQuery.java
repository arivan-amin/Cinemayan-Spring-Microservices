package com.maya.cbs.ledger.domain.studio.query;

import com.maya.cbs.core.domain.pagination.PaginatedResponse;
import com.maya.cbs.core.domain.pagination.PaginationCriteria;
import com.maya.cbs.ledger.domain.studio.entity.Studio;
import com.maya.cbs.ledger.domain.studio.persistence.GetStudiosParams;
import com.maya.cbs.ledger.domain.studio.persistence.StudioStorage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class GetStudiosQuery {

    private final StudioStorage storage;

    @Transactional
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
