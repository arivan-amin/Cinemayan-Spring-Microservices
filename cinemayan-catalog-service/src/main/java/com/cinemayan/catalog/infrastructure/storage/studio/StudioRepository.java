package com.cinemayan.catalog.infrastructure.storage.studio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudioRepository extends JpaRepository<StudioEntity, UUID> {

}
