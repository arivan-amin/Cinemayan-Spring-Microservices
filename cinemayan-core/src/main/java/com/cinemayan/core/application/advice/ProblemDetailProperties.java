package com.cinemayan.core.application.advice;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class ProblemDetailProperties {

    public static final String CATEGORY = "category";
    public static final String TIMESTAMP = "timestamp";

    public static final String TRACE_ID_KEY = "TRACE_ID";
    public static final String SPAN_ID_KEY = "SPAN_ID";

    public static final String TRACE_ID_VALUE = "traceId";
    public static final String SPAN_ID_VALUE = "spanId";
}
