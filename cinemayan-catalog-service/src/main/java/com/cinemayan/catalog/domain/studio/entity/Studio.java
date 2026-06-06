package com.cinemayan.catalog.domain.studio.entity;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Studio {

    private StudioId id;
    private String name;
    private String country;
    private LocalDate foundedDate;
}
