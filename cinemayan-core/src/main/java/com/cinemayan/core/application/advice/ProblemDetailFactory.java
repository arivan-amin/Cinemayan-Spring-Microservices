package com.cinemayan.core.application.advice;

import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;

import static com.cinemayan.core.application.advice.ProblemDetailProperties.*;

@RequiredArgsConstructor
public class ProblemDetailFactory {

    private final Clock clock;

    public ProblemDetail build (ProblemDetailParams params) {
        ProblemDetail problem = ProblemDetail.forStatus(params.getStatus());
        problem.setTitle(params.getTitle());
        problem.setDetail(params.getDetail());
        problem.setType(URI.create(params.getDocUrl()));
        problem.setProperty(CATEGORY, params.getCategory());
        problem.setProperty(TIMESTAMP, Instant.now(clock));
        problem.setProperty(TRACE_ID_KEY, MDC.get(TRACE_ID_VALUE));
        problem.setProperty(SPAN_ID_KEY, MDC.get(SPAN_ID_VALUE));
        return problem;
    }
}
