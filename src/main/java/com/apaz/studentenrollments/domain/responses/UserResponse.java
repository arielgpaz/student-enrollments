package com.apaz.studentenrollments.domain.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String name;

    private String username;

    private String email;

}
