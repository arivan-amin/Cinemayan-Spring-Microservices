package com.maya.cbs.core.domain.aspects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Slf4j
public final class ExecutionWrapper {

    public static <T> T executeThrowable (ThrowableExecutor<T> executor) throws Throwable {
        return executor.execute();
    }

    @FunctionalInterface
    public interface ThrowableExecutor<T> {

        T execute () throws Throwable;
    }
}
