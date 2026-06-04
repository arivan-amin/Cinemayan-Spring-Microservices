package com.cinemayan.catalog.application.request;

import com.cinemayan.catalog.domain.studio.command.create.CreateStudioCommand;
import com.cinemayan.catalog.domain.studio.entity.Studio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CreateStudioRequest {

    @NotBlank
    String name;

    @NotBlank
    String country;

    @Past
    LocalDate foundedDate;

    public CreateStudioCommand.Input toInput () {
        Studio studio = new Studio();
        studio.setName(name);
        studio.setCountry(country);
        studio.setFoundedDate(foundedDate);
        return new CreateStudioCommand.Input(studio);
    }
}
