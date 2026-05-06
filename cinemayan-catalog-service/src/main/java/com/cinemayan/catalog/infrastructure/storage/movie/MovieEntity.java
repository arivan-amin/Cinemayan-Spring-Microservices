package com.cinemayan.catalog.infrastructure.storage.movie;

import com.cinemayan.outbox.infrastructure.audit.AuditFields;
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
public class MovieEntity extends AuditFields {

    @Id
    @UuidGenerator
    @Column (name = "id")
    private UUID id;
}
