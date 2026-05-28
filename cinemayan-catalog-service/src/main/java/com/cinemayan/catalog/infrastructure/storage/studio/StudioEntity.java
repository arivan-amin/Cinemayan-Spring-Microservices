package com.cinemayan.catalog.infrastructure.storage.studio;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.entity.StudioId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static com.cinemayan.core.domain.util.MappingUtility.getIdOrNull;

@Entity
@Table (schema = "catalog", name = "studios")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners (AuditingEntityListener.class)
public class StudioEntity {

    @Id
    @UuidGenerator
    @Column (name = "id")
    private UUID id;

    @NotBlank
    @Column (name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column (name = "country", nullable = false)
    private String country;

    @Past
    @Column (name = "founded_date", nullable = false)
    private LocalDate FoundedDate;

    @CreatedDate
    @Column (name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column (name = "updated_at", nullable = false)
    private Instant updatedAt;

    public static StudioEntity fromDomain (Studio studio) {
        StudioEntity entity = new StudioEntity();
        entity.setId(getIdOrNull(studio.getId()));
        entity.setName(studio.getName());
        entity.setCountry(studio.getCountry());
        entity.setFoundedDate(studio.getFoundedDate());
        return entity;
    }

    public Studio toDomain () {
        return new Studio(StudioId.of(id), name, country, FoundedDate);
    }
}
