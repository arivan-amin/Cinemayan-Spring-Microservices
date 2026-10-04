package com.maya.cbs.core.domain.util;

@FunctionalInterface
public interface DomainId<T> {

    T getValue ();
}
