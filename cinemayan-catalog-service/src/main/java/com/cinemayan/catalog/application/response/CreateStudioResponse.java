package com.cinemayan.catalog.application.response;

import lombok.Value;

import java.util.UUID;

@Value
public class CreateStudioResponse {

    UUID id;

    public static CreateStudioResponse of (UUID id) {
        return new CreateStudioResponse(id);
    }
}
