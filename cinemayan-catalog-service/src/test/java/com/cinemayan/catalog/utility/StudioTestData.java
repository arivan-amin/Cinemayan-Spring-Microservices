package com.cinemayan.catalog.utility;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.domain.studio.entity.StudioId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class StudioTestData {

    public static List<Studio> studiosList () {
        return List.of(withNameAndNoId("20th Century Studios"),
            withNameAndNoId("Warner Bros. Pictures"), withNameAndNoId("New Line Cinema"));
    }

    public static Studio withNameAndNoId (String name) {
        Studio studio = new Studio();
        studio.setId(StudioId.of(UUID.randomUUID()));
        studio.setName(name);
        studio.setCountry("Italy");
        studio.setFoundedDate(LocalDate.of(2010, 5, 7));
        return studio;
    }

    public static Studio withNameAndRandomId (String name) {
        Studio studio = withNameAndNoId(name);
        studio.setId(StudioId.of(UUID.randomUUID()));
        return studio;
    }

    public static Studio withDefaultNameAndNoId () {
        return withNameAndNoId("Universal Pictures");
    }
}
