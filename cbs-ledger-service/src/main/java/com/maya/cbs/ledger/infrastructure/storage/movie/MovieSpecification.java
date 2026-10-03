package com.maya.cbs.ledger.infrastructure.storage.movie;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MovieSpecification implements Specification<MovieEntity> {

    private final String searchQuery;
    private final Instant startDate;
    private final Instant endDate;

    @Override
    public Predicate toPredicate (Root<MovieEntity> root, CriteriaQuery<?> query,
                                  CriteriaBuilder criteria) {

        List<Predicate> predicates =
            Stream.of(bySearchQuery(root, criteria), byCreatedAtStart(root, criteria),
                    byCreatedAtEnd(root, criteria))
                .filter(Objects::nonNull)
                .toList();

        return criteria.and(predicates.toArray(Predicate[]::new));
    }

    private Predicate bySearchQuery (Root<MovieEntity> root, CriteriaBuilder criteria) {
        return Optional.ofNullable(searchQuery)
            .map(_ -> getSearchQueryPattern(root, criteria))
            .orElse(null);
    }

    private Predicate byCreatedAtStart (Root<MovieEntity> root, CriteriaBuilder criteria) {
        return Optional.ofNullable(startDate)
            .map(_ -> criteria.greaterThanOrEqualTo(root.get("createdAt"), startDate))
            .orElse(null);
    }

    private Predicate byCreatedAtEnd (Root<MovieEntity> root, CriteriaBuilder criteria) {
        return Optional.ofNullable(endDate)
            .map(_ -> criteria.lessThan(root.get("createdAt"), endDate))
            .orElse(null);
    }

    private Predicate getSearchQueryPattern (Root<MovieEntity> root, CriteriaBuilder criteria) {
        String likePattern = "%" + searchQuery.toLowerCase(Locale.ENGLISH) + "%";
        return criteria.or(criteria.like(criteria.lower(root.get("firstName")), likePattern),
            criteria.like(criteria.lower(root.get("lastName")), likePattern),
            criteria.like(criteria.lower(root.get("email")), likePattern),
            criteria.like(criteria.lower(root.get("address")), likePattern),
            criteria.like(criteria.lower(root.get("phoneNumber")), likePattern));
    }
}
