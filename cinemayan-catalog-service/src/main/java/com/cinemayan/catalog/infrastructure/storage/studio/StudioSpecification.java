package com.cinemayan.catalog.infrastructure.storage.studio;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class StudioSpecification implements Specification<StudioEntity> {

    private final String searchQuery;
    private final Instant startDate;
    private final Instant endDate;

    @Override
    public Predicate toPredicate (Root<StudioEntity> root, CriteriaQuery<?> query,
                                  CriteriaBuilder criteria) {

        List<Predicate> predicates =
            Stream.of(bySearchQuery(root, criteria), byFoundedDateStart(root, criteria),
                    byFoundedDateEnd(root, criteria))
                .filter(Objects::nonNull)
                .toList();

        return criteria.and(predicates.toArray(Predicate[]::new));
    }

    private Predicate bySearchQuery (Root<StudioEntity> root, CriteriaBuilder criteria) {
        return Optional.ofNullable(searchQuery)
            .map(_ -> getSearchQueryPattern(root, criteria))
            .orElse(null);
    }

    private Predicate byFoundedDateStart (Root<StudioEntity> root, CriteriaBuilder criteria) {
        return Optional.ofNullable(startDate)
            .map(_ -> criteria.greaterThanOrEqualTo(root.get("createdAt"), startDate))
            .orElse(null);
    }

    private Predicate byFoundedDateEnd (Root<StudioEntity> root, CriteriaBuilder criteria) {
        return Optional.ofNullable(endDate)
            .map(_ -> criteria.lessThan(root.get("createdAt"), endDate))
            .orElse(null);
    }

    private Predicate getSearchQueryPattern (Root<StudioEntity> root, CriteriaBuilder criteria) {
        String likePattern = "%" + searchQuery.toLowerCase(Locale.ENGLISH) + "%";
        return criteria.or(criteria.like(criteria.lower(root.get("name")), likePattern),
            criteria.like(criteria.lower(root.get("country")), likePattern));
    }
}
