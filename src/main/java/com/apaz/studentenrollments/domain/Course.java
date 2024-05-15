package com.apaz.studentenrollments.domain;

import com.apaz.studentenrollments.domain.enums.StatusCourseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatusCourseEnum status;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime inactivationDate;

}
