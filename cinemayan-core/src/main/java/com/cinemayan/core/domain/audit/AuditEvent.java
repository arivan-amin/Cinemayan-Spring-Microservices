package com.cinemayan.core.domain.audit;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class AuditEvent {

    String serviceName;
    String location;
    String action;
    String data;
    Instant recordedAt;
    String duration;
    String response;
}
