package com.maya.cbs.ledger.application.config.operation;

import com.maya.cbs.ledger.domain.studio.command.*;
import com.maya.cbs.ledger.domain.studio.persistence.StudioStorage;
import com.maya.cbs.ledger.domain.studio.query.GetStudioByIdQuery;
import com.maya.cbs.ledger.domain.studio.query.GetStudiosQuery;
import com.maya.cbs.ledger.infrastructure.storage.studio.StudioJpaStorage;
import com.maya.cbs.ledger.infrastructure.storage.studio.StudioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StudioOperationsConfig {

    @Bean
    public StudioStorage studioStorage (StudioRepository repository) {
        return new StudioJpaStorage(repository);
    }

    @Bean
    public GetStudiosQuery getStudiosQuery (StudioStorage storage) {
        return new GetStudiosQuery(storage);
    }

    @Bean
    public GetStudioByIdQuery getStudioByIdQuery (StudioStorage storage) {
        return new GetStudioByIdQuery(storage);
    }

    @Bean
    public CreateStudioCommand createStudioCommand (StudioStorage storage) {
        return new CreateStudioCommand(storage);
    }

    @Bean
    public UpdateStudioCommand updateStudioCommand (StudioStorage storage) {
        return new UpdateStudioCommand(storage);
    }

    @Bean
    public DeleteStudioCommand deleteStudioCommand (StudioStorage storage) {
        return new DeleteStudioCommand(storage);
    }
}
