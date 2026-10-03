package com.maya.cbs.ledger.infrastructure.storage.studio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface StudioRepository
    extends JpaRepository<StudioEntity, UUID>, JpaSpecificationExecutor<StudioEntity> {

    Optional<StudioEntity> findByName (String name);
}
