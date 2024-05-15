package com.apaz.studentenrollments.domain.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseNpsResponse {

    private String courseCode;
    private Integer nps;

}
