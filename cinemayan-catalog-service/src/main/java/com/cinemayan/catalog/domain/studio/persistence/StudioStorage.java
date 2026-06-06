package com.cinemayan.catalog.domain.studio.persistence;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.core.domain.pagination.PaginatedResponse;
import com.cinemayan.core.domain.pagination.PaginationCriteria;

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
