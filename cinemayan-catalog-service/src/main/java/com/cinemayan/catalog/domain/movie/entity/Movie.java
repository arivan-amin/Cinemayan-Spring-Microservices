package com.cinemayan.catalog.domain.movie.entity;

import com.cinemayan.catalog.domain.content.*;
import lombok.*;

import java.time.*;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private UUID id;
    private String title;
    private String synopsis;
    private Year releaseYear;
    private Duration runtime;
    private Set<Genre> genres;
    private AgeRating ageRating;
    private Set<ContentWarning> contentWarnings;
    private Set<ProductionCompany> productionCompanies;
    private String posterUrl;
    private String trailerUrl;
    private String imdbId;

    private Instant createdAt;
    private Instant updatedAt;
}
