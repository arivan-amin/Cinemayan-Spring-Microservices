package com.cinemayan.catalog.infrastructure.storage.studio;

import com.cinemayan.catalog.domain.studio.entity.Studio;
import com.cinemayan.catalog.utility.StudioTestData;
import com.cinemayan.testing.architecture.bases.BaseUnitTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class StudioMapperTest implements BaseUnitTest {

    @Test
    void fromDomain_shouldMapAllFields_whenStudioIsValid () {
        // given
        Studio studio = StudioTestData.withNameAndRandomId("Some studio");

        // when
        StudioEntity result = StudioMapper.fromDomain(studio);

        // then
        assertThat(result.getId()).isEqualTo(studio.getId()
            .getValue());
        assertThat(result.getName()).isEqualTo(studio.getName());
        assertThat(result.getCountry()).isEqualTo(studio.getCountry());
        assertThat(result.getFoundedDate()).isEqualTo(studio.getFoundedDate());
    }

    @Test
    void toDomain_shouldMapAllFields_whenStudioEntityIsValid () {
        // given
        StudioEntity entity =
            StudioMapper.fromDomain(StudioTestData.withNameAndRandomId("Another studio"));

        // when
        Studio result = StudioMapper.toDomain(entity);

        // then
        assertThat(result.getId()
            .getValue()).isEqualTo(entity.getId());
        assertThat(result.getName()).isEqualTo(entity.getName());
        assertThat(result.getCountry()).isEqualTo(entity.getCountry());
        assertThat(result.getFoundedDate()).isEqualTo(entity.getFoundedDate());
    }

    @Test
    void roundTrip_shouldPreserveAllFields_whenMappingToDomainAndBack () {
        // given
        Studio studio = StudioTestData.withDefaultNameAndNoId();

        // when
        StudioEntity entity = StudioMapper.fromDomain(studio);
        Studio result = StudioMapper.toDomain(entity);

        // then
        assertThat(result.getId()
            .getValue()).isEqualTo(studio.getId()
            .getValue());
        assertThat(result.getName()).isEqualTo(studio.getName());
        assertThat(result.getCountry()).isEqualTo(studio.getCountry());
        assertThat(result.getFoundedDate()).isEqualTo(studio.getFoundedDate());
    }
}
