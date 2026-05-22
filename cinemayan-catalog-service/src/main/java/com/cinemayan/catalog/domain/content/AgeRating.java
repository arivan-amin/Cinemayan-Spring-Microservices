package com.cinemayan.catalog.domain.content;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AgeRating {
    G("G", "General Audiences", 0),
    PG("PG", "Parental Guidance Suggested", 8),
    PG_13("PG-13", "Parents Strongly Cautioned", 13),
    R("R", "Restricted", 17),
    NC_17("NC-17", "Adults Only", 18),
    TV_Y("TV-Y", "All Children", 0),
    TV_Y7("TV-Y7", "Directed to Older Children", 7),
    TV_G("TV-G", "General Audience", 0),
    TV_PG("TV-PG", "Parental Guidance Suggested", 8),
    TV_14("TV-14", "Parents Strongly Cautioned", 14),
    TV_MA("TV-MA", "Mature Audience Only", 17),
    NOT_RATED("NOT_RATED", "Not Rated", 0),
    UNRATED("UNRATED", "Unrated", 0);

    private final String code;
    private final String description;
    private final int minimumAge;
}
