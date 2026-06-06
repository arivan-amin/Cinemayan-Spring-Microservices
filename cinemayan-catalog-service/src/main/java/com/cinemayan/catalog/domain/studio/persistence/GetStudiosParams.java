package com.cinemayan.catalog.domain.studio.persistence;

import lombok.Value;

import java.time.Instant;

@Value
public class GetStudiosParams {

    String searchQuery;
    Instant startDate;
    Instant endDate;
}
