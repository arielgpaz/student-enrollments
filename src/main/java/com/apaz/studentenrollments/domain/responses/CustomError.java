package com.apaz.studentenrollments.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CustomError {

    private final String message;
    private final Integer status;
    private final LocalDateTime timestamp;

}
