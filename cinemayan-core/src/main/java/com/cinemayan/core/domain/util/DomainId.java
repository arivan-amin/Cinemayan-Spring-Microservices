package com.cinemayan.core.domain.util;

@FunctionalInterface
public interface DomainId<T> {

    T getValue ();
}
