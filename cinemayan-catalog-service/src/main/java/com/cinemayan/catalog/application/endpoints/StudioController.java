package com.cinemayan.catalog.application.endpoints;

import com.cinemayan.catalog.application.config.StudioApiURLs;
import com.cinemayan.catalog.application.config.StudioCaches;
import com.cinemayan.catalog.application.request.CreateStudioRequest;
import com.cinemayan.catalog.application.response.CreateStudioResponse;
import com.cinemayan.catalog.domain.studio.command.create.CreateStudioCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag (name = "Studio Controller")
@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
class StudioController {

    private final CreateStudioCommand createCommand;

    @PostMapping (StudioApiURLs.CREATE_STUDIO_URL)
    @Operation (summary = "Creates a studio")
    @CacheEvict (cacheNames = { StudioCaches.GET_ALL_STUDIOS, StudioCaches.GET_STUDIO_BY_ID },
        allEntries = true)
    @ResponseStatus (HttpStatus.CREATED)
    public CreateStudioResponse createStudio (@RequestBody @Valid CreateStudioRequest request) {
        CreateStudioCommand.Output output = createCommand.execute(request.toInput());
        return CreateStudioResponse.of(output.getId());
    }
}
