package com.cinemayan.catalog.infrastructure.storage.movie;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table (name = "movies")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString (callSuper = true)
public class MovieEntity {

    @Id
    @UuidGenerator
    @Column (name = "id")
    private UUID id;
}
