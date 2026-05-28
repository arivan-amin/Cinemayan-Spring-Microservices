package com.cinemayan.catalog.infrastructure.storage.movie;

import lombok.*;

import java.util.UUID;

// @Entity
// @Table (schema = "catalog", name = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {

    // @Id
    // @UuidGenerator
    // @Column (name = "id")
    private UUID id;
}
