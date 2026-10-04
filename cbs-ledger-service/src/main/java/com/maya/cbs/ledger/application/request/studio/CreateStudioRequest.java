package com.maya.cbs.ledger.application.request.studio;

import com.maya.cbs.ledger.domain.studio.command.CreateStudioCommand;
import com.maya.cbs.ledger.domain.studio.entity.Studio;
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
        studio.setName(name.trim());
        studio.setCountry(country.trim());
        studio.setFoundedDate(foundedDate);
        return new CreateStudioCommand.Input(studio);
    }
}
