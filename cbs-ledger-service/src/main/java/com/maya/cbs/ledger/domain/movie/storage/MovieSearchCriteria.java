package com.maya.cbs.ledger.domain.movie.storage;

import com.maya.cbs.core.domain.gender.Gender;
import lombok.Value;

import java.time.Instant;

@Value
public class MovieSearchCriteria {

    String searchQuery;
    Gender gender;
    Instant startDate;
    Instant endDate;
}
