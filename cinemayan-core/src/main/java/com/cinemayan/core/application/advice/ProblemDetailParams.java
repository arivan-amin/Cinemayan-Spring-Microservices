package com.cinemayan.core.application.advice;

import lombok.*;
import org.springframework.http.HttpStatus;

@Value
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Builder
public class ProblemDetailParams {

    HttpStatus status;
    String title;
    String detail;
    String category;
    String docUrl;
}
