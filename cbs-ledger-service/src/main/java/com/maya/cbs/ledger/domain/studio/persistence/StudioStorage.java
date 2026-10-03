package com.maya.cbs.ledger.domain.studio.persistence;

import com.maya.cbs.core.domain.pagination.PaginatedResponse;
import com.maya.cbs.core.domain.pagination.PaginationCriteria;
import com.maya.cbs.ledger.domain.studio.entity.Studio;

import java.util.Optional;
import java.util.UUID;

public interface StudioStorage {

    PaginatedResponse<Studio> findAll (GetStudiosParams params, PaginationCriteria criteria);

    Optional<Studio> findById (UUID id);

    Optional<Studio> findByName (String name);

    Studio create (Studio studio);

    Studio update (Studio studio);

    void delete (UUID id);
}
